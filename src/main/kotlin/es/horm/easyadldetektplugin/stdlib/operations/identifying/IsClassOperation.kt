package es.horm.easyadldetektplugin.stdlib.operations.identifying

import es.horm.easyadldetektplugin.model.ExecutionScope
import es.horm.easyadldetektplugin.model.IdentifyingEasyAdlOperation
import org.jetbrains.kotlin.descriptors.ClassDescriptor
import org.jetbrains.kotlin.descriptors.isClass
import org.jetbrains.kotlin.psi.KtClass
import org.jetbrains.kotlin.psi.KtElement
import org.jetbrains.kotlin.psi.KtSimpleNameExpression
import org.jetbrains.kotlin.psi.KtTypeReference
import org.jetbrains.kotlin.resolve.bindingContextUtil.getAbbreviatedTypeOrType
import org.jetbrains.kotlin.resolve.calls.util.getResolvedCall
import org.jetbrains.kotlin.util.isOrdinaryClass

class IsClassOperation : IdentifyingEasyAdlOperation {

    companion object {

        private val spellings = listOf("is class")

        fun matchesTokenText(tokenText: String): Boolean = spellings.any { it.equals(tokenText, true) }
    }

    override fun identify(ktElement: KtElement, executionScope: ExecutionScope): Boolean =
        (ktElement as? KtClass)?.isOrdinaryClass ?: false

    override fun identifyReference(ktElement: KtElement, executionScope: ExecutionScope): Boolean =
        ((ktElement as? KtTypeReference)
            ?.getAbbreviatedTypeOrType(executionScope.bindingContext)
            ?.constructor
            ?.declarationDescriptor as? ClassDescriptor)
            ?.kind
            ?.isClass ?:
        ((ktElement as? KtSimpleNameExpression)
            ?.getResolvedCall(executionScope.bindingContext)
            ?.resultingDescriptor
            ?.returnType
            ?.constructor
            ?.declarationDescriptor as? ClassDescriptor)
            ?.kind
            ?.isClass
        ?: false
}
