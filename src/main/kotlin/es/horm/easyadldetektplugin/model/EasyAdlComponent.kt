package es.horm.easyadldetektplugin.model

import org.jetbrains.kotlin.psi.KtElement

data class EasyAdlComponent(
    val name: String,
    val easyAdlOperations: List<EasyAdlOperation>
) : ArchitectureFragment {

    fun canComponentBeIdentified(ktElement: KtElement, executionScope: ExecutionScope): Boolean =
        easyAdlOperations
            .filterIsInstance<IdentifyingEasyAdlOperation>()
            .all { it.identify(ktElement, executionScope) }

    fun doesComponentComply(ktElement: KtElement, executionScope: ExecutionScope): Boolean =
        easyAdlOperations
            .filterIsInstance<RuleEasyAdlOperation>()
            .all { it.complies(ktElement, executionScope) }

    fun getErrorMessages(ktElement: KtElement, executionScope: ExecutionScope) : List<String> =
        easyAdlOperations
            .filterIsInstance<RuleEasyAdlOperation>()
            .filter { !it.complies(ktElement, executionScope) }
            .map { it.errorMessage }
}
