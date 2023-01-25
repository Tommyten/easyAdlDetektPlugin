package es.horm.easyadldetektplugin

import es.horm.easyadldetektplugin.interpreter.interpretArchitectureDescription
import io.gitlab.arturbosch.detekt.api.Config
import io.gitlab.arturbosch.detekt.rules.KotlinCoreEnvironmentTest
import io.gitlab.arturbosch.detekt.test.compileAndLintWithContext
import io.kotest.matchers.collections.shouldHaveSize
import org.jetbrains.kotlin.cli.jvm.compiler.KotlinCoreEnvironment
import org.junit.jupiter.api.Test

@KotlinCoreEnvironmentTest
internal class EasyAdlComplianceRuleTest(private val env: KotlinCoreEnvironment) {

    @Test
    fun `reports inner classes`() {
        val architectureDescription = """
component EasyAdlArchitectureReader:
  has suffix "EasyAdlArchitectureReader"
  may not reference component NotInSystem
  
component FunTest:
  has suffix "Fun"
            
component NotInSystem:
  has suffix "Foo"
  is class
  is not inner class
  must have inner class
  must reference component EasyAdlArchitectureReader
""".trimIndent()

        val code = """
        package es.horm.test

        @Composable
        fun blaFun() {
        
        }

        class AFoo {
            val bla = BlaTest()
            val test = List()
            inner class BFoo {
                
            }
        }

        class TestFoo {
            fun asdf(){
            blaFun()}
        }

        class BlaTest {
            val aFoo = AFoo()
        }

        annotation class Composable
        """
        val findings = EasyAdlComplianceRule(Config.empty).compileAndLintWithContext(env, code)
        findings shouldHaveSize 3
    }

    @Test
    fun `mvvm example successful`() {
        val architectureDescription = """
            component ViewModel:
              has suffix "ViewModel"
              must reference component Repository
              
            component Repository:
              has suffix "Repository"

            component UI:
              has Suffix "Screen"
              is annotated with "Composable"
              must reference component ViewModel
              may not reference component Repository
        """.trimIndent()

        val code = """
class TestRepository {
    
}

class TestViewModel {
    val repository = TestRepository()
}

@Composable
fun TestScreen() {
    val viewModel = TestViewModel()
    //val repository = TestRepository() uncommenting breaks test
}

annotation class Composable
        """
        val rule = EasyAdlComplianceRule(Config.empty)
        val findings = rule.compileAndLintWithContext(env, code)
        findings shouldHaveSize 0
    }

    @Test
    fun `MVVM from Plugin Test`() {
        val architectureDescriptionText = """
system MVVM:
    component ViewModel:
        has suffix "ViewModel"
        must reference component Repository

    component Repository:
        has suffix "Repository"

    component UI:
        has Suffix "Screen"
        must reference component ViewModel
        must reference component Repository
        """.trimIndent()

        val code = """
package org.example.mvvm

class SampleViewModel {

    private val repository = SampleRepository()

    fun doSampleBusinessLogic(): Int {
        return repository.getData()
    }
}


class SampleView {

    private val sampleViewModel = SampleViewModel()

    fun render() {
        println(sampleViewModel.doSampleBusinessLogic())
    }
}

interface Repository

const val UPPER_BOUND = 10

class SampleRepository : Repository {
    val testView = SampleView()

    init {
        testView.render()
    }

    fun getData(): Int = (0..UPPER_BOUND).random()
}
        """
        val rule = EasyAdlComplianceRule(Config.empty).apply {
            architectureDescription = interpretArchitectureDescription(architectureDescriptionText)
        }
        val findings = rule.compileAndLintWithContext(env, code)
        findings shouldHaveSize 0
    }

    @Test
    fun `bla test remove me`() {
        val architectureDescription = """
system MVVM:
    component ViewModel:
        has suffix "ViewModel"
        must reference component Repository

    component Repository:
        has suffix "Repository"

    component UI:
        has Suffix "Screen"
        must reference component ViewModel
        must reference component Repository
        """.trimIndent()

        val code = """
class A

class B {
    val a = A()
}
        """
        val rule = EasyAdlComplianceRule(Config.empty)
        val findings = rule.compileAndLintWithContext(env, code)
        findings shouldHaveSize 0
    }

    // region newtest

    @Test
    fun `may not reference test`(){
        val architectureDescriptionText = """
system MVVM:
    component UI:
        has Suffix "View"
        is class
        must reference component ViewModel
        may not reference component Repository

    component ViewModel:
        has suffix "ViewModel"
        is class
        must reference component Repository

    Component Repository:
        has suffix "Repository"
        is class
        """.trimIndent()

        val code = """
package org.example.mvvm

class SampleView {

    private val sampleViewModel = SampleViewModel()

    fun render() {
        println(sampleViewModel.doSampleBusinessLogic())
    }
}

class SampleViewModel {

    private val repository = SampleRepository()

    fun doSampleBusinessLogic(): Int {
        return repository.getData()
    }
}

const val UPPER_BOUND = 10

class SampleRepository {

    fun getData(): Int = (0..UPPER_BOUND).random()
}
        """
        val rule = EasyAdlComplianceRule(Config.empty).apply {
            architectureDescription = interpretArchitectureDescription(architectureDescriptionText)
        }
        val findings = rule.compileAndLintWithContext(env, code)
        findings shouldHaveSize 0
    }

    // endregion

}
