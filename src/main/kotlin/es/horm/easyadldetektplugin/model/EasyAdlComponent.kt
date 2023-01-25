package es.horm.easyadldetektplugin.model

import org.jetbrains.kotlin.psi.KtElement

data class EasyAdlComponent(
    val name: String,
    val easyAdlOperations: List<EasyAdlOperation>
) : ArchitectureFragment {

    fun canComponentBeIdentified(element: KtElement, executionScope: ExecutionScope): Boolean =
        easyAdlOperations
            .filterIsInstance<IdentifyingEasyAdlOperation>()
            .all { it.identify(element, executionScope) }

    fun doesComponentComply(element: KtElement, executionScope: ExecutionScope): Boolean =
        easyAdlOperations
            .filterIsInstance<RuleEasyAdlOperation>()
            .all { it.complies(element, executionScope) }
}
