package es.horm.easyadldetektplugin.detekt.rule

import es.horm.easyadldetektplugin.detekt.ArchitectureError
import es.horm.easyadldetektplugin.detekt.config.EasyAdlArchitectureHolder
import es.horm.easyadldetektplugin.model.ArchitectureDescription
import es.horm.easyadldetektplugin.model.ExecutionScope
import io.gitlab.arturbosch.detekt.api.Config
import io.gitlab.arturbosch.detekt.api.Debt
import io.gitlab.arturbosch.detekt.api.Entity
import io.gitlab.arturbosch.detekt.api.Issue
import io.gitlab.arturbosch.detekt.api.Rule
import io.gitlab.arturbosch.detekt.api.Severity
import io.gitlab.arturbosch.detekt.api.internal.RequiresTypeResolution
import org.jetbrains.kotlin.psi.KtElement
import org.jetbrains.kotlin.psi.KtNamedDeclaration

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

    override fun visitKtElement(ktElement: KtElement) {
        val executionScope = ExecutionScope(bindingContext, architectureDescription)

        val easyAdlComponents = architectureDescription.getAllComponents()
        val identified = easyAdlComponents.filter { it.canComponentBeIdentified(ktElement, executionScope) }
        for (component in identified) {
            println("Identified a component!")
            val doesComply = component.doesComponentComply(ktElement, executionScope)
            if (!doesComply) {
                println("The component does not comply!")
                val entity = if(ktElement is KtNamedDeclaration) Entity.atName(ktElement) else Entity.from(ktElement)
                 component.getErrorMessages(ktElement, executionScope)
                     .forEach { report(ArchitectureError(component.name, issue, entity, it)) }
            }
        }

        super.visitKtElement(ktElement)
    }
}
