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

class MustReferenceOperation(
    private val componentArgument: ComponentArgument
) : RuleEasyAdlOperation, HasMermaidFlowChartRepresentation {

    override val errorMessage = "This component does not reference the component ${componentArgument.componentName}."

    companion object {

        private val spellings = listOf("must reference")

        fun matchesTokenText(tokenText: String): Boolean = spellings.any { it.equals(tokenText, true) }
    }

    override fun complies(ktElement: KtElement, executionScope: ExecutionScope): Boolean {
        val referencedComponent = executionScope.getComponentByName(componentArgument.componentName) ?: return false
        val referenceExpressions = ktElement.collectDescendantsOfType<KtReferenceExpression>()
        val identificationOperationsOfReferenced =
            referencedComponent.easyAdlOperations.filterIsInstance<IdentifyingEasyAdlOperation>()

        return referenceExpressions.any { refExpr ->
            identificationOperationsOfReferenced.all { idOp ->
                idOp.identifyReference(refExpr, executionScope) ?: true
            }
        }
    }

    override fun getMermaidFlowChartRepresentation(owningComponent: EasyAdlComponent): String =
        "${owningComponent.name}==>${componentArgument.componentName}"
}
