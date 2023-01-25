package es.horm.easyadldetektplugin.model

import org.jetbrains.kotlin.psi.KtElement

sealed interface EasyAdlOperation
interface IdentifyingEasyAdlOperation : EasyAdlOperation {
    fun identify(ktElement: KtElement, executionScope: ExecutionScope): Boolean
    fun identifyReference(ktElement: KtElement, executionScope: ExecutionScope): Boolean? = null // TODO: update this in thesis as well
}
interface RuleEasyAdlOperation : EasyAdlOperation {
    fun complies(ktElement: KtElement, executionScope: ExecutionScope): Boolean
}

interface OperationFactory {
    fun createOperation(tokenText: String, arguments: List<Argument>): EasyAdlOperation

    fun canCreateOperation(tokenText: String): Boolean
}
