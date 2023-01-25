package es.horm.easyadldetektplugin

import es.horm.easyadldetektplugin.config.EasyAdlArchitectureHolder
import es.horm.easyadldetektplugin.model.ArchitectureDescription
import es.horm.easyadldetektplugin.model.ExecutionScope
import io.gitlab.arturbosch.detekt.api.Config
import io.gitlab.arturbosch.detekt.api.Debt
import io.gitlab.arturbosch.detekt.api.Entity
import io.gitlab.arturbosch.detekt.api.Issue
import io.gitlab.arturbosch.detekt.api.Rule
import io.gitlab.arturbosch.detekt.api.Severity
import io.gitlab.arturbosch.detekt.api.internal.RequiresTypeResolution
import org.jetbrains.kotlin.psi.KtCallExpression
import org.jetbrains.kotlin.psi.KtElement

@RequiresTypeResolution
class EasyAdlComplianceRule(
    config: Config
) : Rule(config) {

    override val issue = Issue(
        javaClass.simpleName,
        Severity.Maintainability,
        "Detekt Compliance Rule",
        Debt.TWENTY_MINS,
    )

    var architectureDescription: ArchitectureDescription = EasyAdlArchitectureHolder.architectureDescription ?: ArchitectureDescription(listOf())

    override fun visitKtElement(element: KtElement) {
        val executionScope = ExecutionScope(bindingContext, architectureDescription)

        val easyAdlComponents = architectureDescription.getAllComponents()
        val identified = easyAdlComponents.filter { it.canComponentBeIdentified(element, executionScope) }
        for (component in identified) {
            println("Identified a component!")
            val doesComply = component.doesComponentComply(element, executionScope)
            if (!doesComply) {
                println("The component does not comply!")
                report(ArchitectureError(issue, Entity.from(element), "Element does not comply to architecture description"))
            }
        }

        super.visitKtElement(element)
    }
}
