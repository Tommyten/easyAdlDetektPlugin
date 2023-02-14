package es.horm.easyadldetektplugin.stdlib.operations.rule

import es.horm.easyadldetektplugin.mermaid.HasMermaidFlowChartRepresentation
import es.horm.easyadldetektplugin.model.ComponentArgument
import es.horm.easyadldetektplugin.model.EasyAdlComponent
import es.horm.easyadldetektplugin.model.ExecutionScope
import es.horm.easyadldetektplugin.model.RuleEasyAdlOperation
import org.jetbrains.kotlin.psi.KtElement

class MayReferenceOperation(
    private val componentArgument: ComponentArgument
) : RuleEasyAdlOperation, HasMermaidFlowChartRepresentation {


    override val errorMessage: String
        get() = throw IllegalStateException("May Reference cant not comply, thus this error message should never have to be used")

    companion object {

        private val spellings = listOf("may reference", "can reference", "might reference")

        fun matchesTokenText(tokenText: String): Boolean = spellings.any { it.equals(tokenText, true) }
    }

    override fun complies(ktElement: KtElement, executionScope: ExecutionScope): Boolean = true

    override fun getMermaidFlowChartRepresentation(owningComponent: EasyAdlComponent): String =
        "${owningComponent.name}-->${componentArgument.componentName}"
}
