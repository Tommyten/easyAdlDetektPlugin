package es.horm.easyadldetektplugin

import io.gitlab.arturbosch.detekt.api.Config
import io.gitlab.arturbosch.detekt.rules.KotlinCoreEnvironmentTest
import io.gitlab.arturbosch.detekt.test.compileAndLintWithContext
import io.kotest.matchers.collections.shouldHaveSize
import org.jetbrains.kotlin.cli.jvm.compiler.KotlinCoreEnvironment
import org.junit.jupiter.api.Test

@KotlinCoreEnvironmentTest
internal class MyRuleTest(private val env: KotlinCoreEnvironment) {

    @Test
    fun `reports inner classes`() {
        val architectureDescription = """
component Test:
  has suffix "Test"
  may not reference component NotInSystem
  
component FunTest:
  has suffix "Fun"
            
component NotInSystem:
  has suffix "Foo"
  is class
  is not inner class
  must have inner class
  must reference component Test
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
        val findings = MyRule(Config.empty, architectureDescription).compileAndLintWithContext(env, code)
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
}

annotation class Composable
        """
        val findings = MyRule(Config.empty, architectureDescription).compileAndLintWithContext(env, code)
        findings shouldHaveSize 0
    }
}
