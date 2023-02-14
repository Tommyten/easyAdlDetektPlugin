package es.horm.easyadldetektplugin.stdlib.operations.identifying

import es.horm.easyadldetektplugin.model.ExecutionScope
import es.horm.easyadldetektplugin.model.IdentifyingEasyAdlOperation
import org.jetbrains.kotlin.psi.KtClass
import org.jetbrains.kotlin.psi.KtElement
import org.jetbrains.kotlin.util.isOrdinaryClass

class IsInterfaceOrClassOperation : IdentifyingEasyAdlOperation {

    companion object {

        private val spellings = listOf("is interface or class", "is class or interface")

        fun matchesTokenText(tokenText: String): Boolean = spellings.any { it.equals(tokenText, true) }
    }

    override fun identify(ktElement: KtElement, executionScope: ExecutionScope): Boolean =
        (ktElement as? KtClass)?.let {
            it.isOrdinaryClass || it.isInterface()
        } ?: false
}
