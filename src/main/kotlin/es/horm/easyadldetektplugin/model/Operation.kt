package es.horm.easyadldetektplugin.model

import org.jetbrains.kotlin.psi.KtElement

interface Operation
interface IdentifyingOperation : Operation {
    fun identify(ktElement: KtElement): Boolean
}
interface RuleOperation : Operation {
    fun complies(ktElement: KtElement): Boolean
}

interface OperationFactory {
    fun createOperation(tokenText: String, arguments: List<String>): Operation

    fun canCreateOperation(tokenText: String): Boolean
}
