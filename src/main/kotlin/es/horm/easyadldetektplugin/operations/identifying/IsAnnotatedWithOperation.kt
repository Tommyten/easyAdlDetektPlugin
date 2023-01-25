package es.horm.easyadldetektplugin.operations.identifying

import es.horm.easyadldetektplugin.mermaid.HasMermaidFlowChartRepresentation
import es.horm.easyadldetektplugin.model.EasyAdlComponent
import es.horm.easyadldetektplugin.model.ExecutionScope
import es.horm.easyadldetektplugin.model.IdentifyingEasyAdlOperation
import es.horm.easyadldetektplugin.model.StringArgument
import org.jetbrains.kotlin.psi.KtElement
import org.jetbrains.kotlin.psi.KtNameReferenceExpression
import org.jetbrains.kotlin.psi.KtNamedFunction
import org.jetbrains.kotlin.resolve.calls.util.getResolvedCall

class IsAnnotatedWithOperation(private val stringArgument: StringArgument) : IdentifyingEasyAdlOperation,
    HasMermaidFlowChartRepresentation {

    companion object {

        private val spellings = listOf("is annotated with")

        fun matchesTokenText(tokenText: String): Boolean = spellings.any { it.equals(tokenText, true) }
    }

    override fun identify(ktElement: KtElement, executionScope: ExecutionScope): Boolean =
        if(ktElement is KtNamedFunction) {
            ktElement.annotationEntries.any { it.shortName?.toString()?.equals(stringArgument.value) ?: false }
        } else false


    override fun identifyReference(ktElement: KtElement, executionScope: ExecutionScope): Boolean? =
        if(ktElement is KtNameReferenceExpression) {
            ktElement.getResolvedCall(executionScope.bindingContext)?.resultingDescriptor?.annotations?.any { annotation ->
                annotation.fqName.toString().endsWith(stringArgument.value)
            } ?: false
        } else false

    //TODO: diese mermaid representation ist wahrscheinlich nicht sehr sinnvoll, vielleicht als anschauungsbeispiel verwenden?
    override fun getMermaidFlowChartRepresentation(owningComponent: EasyAdlComponent): String =
        "${owningComponent.name} -.- ${stringArgument.value}(\"@${stringArgument.value}\")"
}
