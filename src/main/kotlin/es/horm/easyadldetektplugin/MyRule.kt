package es.horm.easyadldetektplugin

import es.horm.easyadldetektplugin.interpreter.interpretArchitectureDescription
import es.horm.easyadldetektplugin.model.ArchitectureDescription
import es.horm.easyadldetektplugin.model.EasyAdlComponent
import es.horm.easyadldetektplugin.model.EasyAdlOperation
import es.horm.easyadldetektplugin.model.ExecutionScope
import io.gitlab.arturbosch.detekt.api.CodeSmell
import io.gitlab.arturbosch.detekt.api.Config
import io.gitlab.arturbosch.detekt.api.Debt
import io.gitlab.arturbosch.detekt.api.Entity
import io.gitlab.arturbosch.detekt.api.Issue
import io.gitlab.arturbosch.detekt.api.Rule
import io.gitlab.arturbosch.detekt.api.Severity
import io.gitlab.arturbosch.detekt.api.internal.RequiresTypeResolution
import org.jetbrains.kotlin.name.FqName
import org.jetbrains.kotlin.psi.KtElement
import org.jetbrains.kotlin.psi.KtNamedDeclaration

@RequiresTypeResolution
class MyRule(
    config: Config,
    val architectureDescription: String
) : Rule(config) {

    override val issue = Issue(
        javaClass.simpleName,
        Severity.CodeSmell,
        "Custom Rule",
        Debt.FIVE_MINS,
    )

    override fun visitKtElement(element: KtElement) {
        super.visitKtElement(element)
        val architectureDescription = interpretArchitectureDescription(architectureDescription)
        val executionScope = ExecutionScope(bindingContext, architectureDescription)

        val easyAdlComponents = architectureDescription.architectureFragments.filterIsInstance<EasyAdlComponent>()
        val identified = easyAdlComponents.filter { it.canComponentBeIdentified(element, executionScope) }
        for (component in identified) {
            val doesComply = component.doesComponentComply(element, executionScope)
            if (!doesComply) {
                report(CodeSmell(issue, Entity.from(element), "Element does not comply to architecture description"))
            }
            println("${element.text} could be identified does it comply? $doesComply")
        }
    }

}
