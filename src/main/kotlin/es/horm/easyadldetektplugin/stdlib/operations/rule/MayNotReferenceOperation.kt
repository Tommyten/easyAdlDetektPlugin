package es.horm.easyadldetektplugin.stdlib.operations.rule

import es.horm.easyadldetektplugin.mermaid.HasMermaidFlowChartRepresentation
import es.horm.easyadldetektplugin.model.ComponentArgument
import es.horm.easyadldetektplugin.model.EasyAdlComponent
import es.horm.easyadldetektplugin.model.ExecutionScope
import es.horm.easyadldetektplugin.model.IdentifyingEasyAdlOperation
import es.horm.easyadldetektplugin.model.RuleEasyAdlOperation
import org.jetbrains.kotlin.psi.KtElement
import org.jetbrains.kotlin.psi.KtReferenceExpression
import org.jetbrains.kotlin.psi.psiUtil.collectDescendantsOfType

class MayNotReferenceOperation(private val componentArgument: ComponentArgument, override val modifiers: List<String>) : RuleEasyAdlOperation,
    HasMermaidFlowChartRepresentation {

    companion object {
        private val spellings = listOf("may not reference", "shall not reference", "must not reference")

        fun matchesTokenText(tokenText: String): Boolean = spellings.any { it.equals(tokenText, true) }
    }

    override val errorMessage = "This component may not reference the component ${componentArgument.componentName}"

    override fun complies(ktElement: KtElement, executionScope: ExecutionScope): Boolean {
        val referencedComponent = executionScope.getComponentByName(componentArgument.componentName) ?: return false
        val referenceExpressions = ktElement.collectDescendantsOfType<KtReferenceExpression>()
        val identificationOperationsOfReferenced =
            referencedComponent.easyAdlOperations.filterIsInstance<IdentifyingEasyAdlOperation>()

        return referenceExpressions.none { refExpr ->
            identificationOperationsOfReferenced.all { idOp ->
                idOp.identifyReference(refExpr, executionScope) ?: true
            }
        }
    }

    override fun getMermaidFlowChartRepresentation(owningComponent: EasyAdlComponent): String? =
        if(modifiers.none{ it.equals("hidden", ignoreCase = true) }) {
            "${owningComponent.name} --x ${componentArgument.componentName}"
        } else null
}
