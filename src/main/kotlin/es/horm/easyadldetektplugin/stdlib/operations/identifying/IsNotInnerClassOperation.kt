package es.horm.easyadldetektplugin.stdlib.operations.identifying

import es.horm.easyadldetektplugin.model.ExecutionScope
import es.horm.easyadldetektplugin.model.IdentifyingEasyAdlOperation
import org.jetbrains.kotlin.psi.KtClass
import org.jetbrains.kotlin.psi.KtElement

class IsNotInnerClassOperation : IdentifyingEasyAdlOperation {

    companion object {

        private val spellings = listOf("is not inner class")

        fun matchesTokenText(tokenText: String): Boolean = spellings.any { it.equals(tokenText, true) }
    }

    override fun identify(ktElement: KtElement, executionScope: ExecutionScope): Boolean =
        ktElement is KtClass && !ktElement.isInner()

    // override fun identifyReference(ktElement: KtElement, executionScope: ExecutionScope): Boolean = true TODO: check, i think this may be possible
}
