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
        val code = """
        class AFoo {
            inner class BFoo {
                
            }
        }

        class TestFoo {
        
        }

        fun blaFoo() {
        
        }
        """
        val findings = MyRule(Config.empty).compileAndLintWithContext(env, code)
        findings shouldHaveSize 3
    }

    @Test
    fun `doesn't report inner classes`() {
        val code = """
        class A {
          class B
        }
        """
        val findings = MyRule(Config.empty).compileAndLintWithContext(env, code)
        findings shouldHaveSize 0
    }
}
