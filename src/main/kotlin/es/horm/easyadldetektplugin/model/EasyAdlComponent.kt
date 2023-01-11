package es.horm.easyadldetektplugin.model

import org.jetbrains.kotlin.psi.KtElement

data class EasyAdlComponent(
    val name: String,
    val easyAdlOperations: List<EasyAdlOperation>
) : ArchitectureFragment {

    fun canComponentBeIdentified(element: KtElement, executionScope: ExecutionScope): Boolean {
        val identifyingOperations = easyAdlOperations.filterIsInstance<IdentifyingEasyAdlOperation>()
        return identifyingOperations.all { it.identify(element, executionScope) }
    }

    fun doesComponentComply(element: KtElement, executionScope: ExecutionScope): Boolean {
        val ruleOperations = easyAdlOperations.filterIsInstance<RuleEasyAdlOperation>()

        return ruleOperations.all { it.complies(element, executionScope) }
    }
}
