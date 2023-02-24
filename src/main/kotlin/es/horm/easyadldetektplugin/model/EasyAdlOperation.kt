package es.horm.easyadldetektplugin.model

import org.jetbrains.kotlin.psi.KtElement

sealed interface EasyAdlOperation {
    val modifiers: List<String>
        get() = listOf()
}
interface IdentifyingEasyAdlOperation : EasyAdlOperation {
    fun identify(ktElement: KtElement, executionScope: ExecutionScope): Boolean
    fun identifyReference(ktElement: KtElement, executionScope: ExecutionScope): Boolean? = null
}
interface RuleEasyAdlOperation : EasyAdlOperation {
    val errorMessage: String
    fun complies(ktElement: KtElement, executionScope: ExecutionScope): Boolean
}

interface OperationFactory {
    fun createOperation(tokenText: String, arguments: List<Argument>, modifiers: List<String>): EasyAdlOperation

    fun canCreateOperation(tokenText: String): Boolean
}
