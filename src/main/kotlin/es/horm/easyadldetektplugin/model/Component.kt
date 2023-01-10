package es.horm.easyadldetektplugin.model

import org.jetbrains.kotlin.psi.KtElement

data class Component(
    val name: String,
    val operations: List<Operation>
) : ArchitectureFragment {

    fun canComponentBeIdentified(element: KtElement): Boolean {
        val identifyingOperations = operations.filterIsInstance<IdentifyingOperation>()
        return identifyingOperations.all {it.identify(element)}
    }

    fun doesComponentComply(element: KtElement): Boolean {
        val ruleOperations = operations.filterIsInstance<RuleOperation>()
        return ruleOperations.all { it.complies(element) }
    }
}
