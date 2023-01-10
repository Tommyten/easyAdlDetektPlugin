package es.horm.easyadldetektplugin

import es.horm.easyadldetektplugin.interpreter.EasyAdlInterpreter
import es.horm.easyadldetektplugin.model.Component
import io.gitlab.arturbosch.detekt.api.CodeSmell
import io.gitlab.arturbosch.detekt.api.Config
import io.gitlab.arturbosch.detekt.api.Debt
import io.gitlab.arturbosch.detekt.api.Entity
import io.gitlab.arturbosch.detekt.api.Issue
import io.gitlab.arturbosch.detekt.api.Rule
import io.gitlab.arturbosch.detekt.api.Severity
import org.jetbrains.kotlin.psi.KtElement

class MyRule(config: Config) : Rule(config) {
    override val issue = Issue(
        javaClass.simpleName,
        Severity.CodeSmell,
        "Custom Rule",
        Debt.FIVE_MINS,
    )

    override fun visitKtElement(element: KtElement) {
        super.visitKtElement(element)
        println("visitNamedDeclaration")
        val input = """
component NotInSystem:
  has suffix "Foo"
  is class
  is not inner class
  must have inner class
""".trimIndent()
        val architectureDescription = EasyAdlInterpreter().interpretArchitectureDescription(input)

        val components = architectureDescription.architectureFragments.filterIsInstance<Component>()
        val identified = components.any { it.canComponentBeIdentified(element) }
        if(identified) {
            val doesComply = components.all { it.doesComponentComply(element) }
            if(!doesComply) {
                report(CodeSmell(issue, Entity.from(element), "Element does not comply to architecture description"))
            }
            println("${element.text} could be identified does it comply? $doesComply")
        }
    }

    /*override fun visitClass(klass: KtClass) {
        super.visitClass(klass)

        if (klass.isInner()) {
            report(CodeSmell(issue, Entity.atName(klass), "Custom message"))
        }
    }*/
}
