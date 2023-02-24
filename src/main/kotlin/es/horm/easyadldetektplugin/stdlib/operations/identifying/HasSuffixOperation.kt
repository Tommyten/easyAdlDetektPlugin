package es.horm.easyadldetektplugin.stdlib.operations.identifying

import es.horm.easyadldetektplugin.model.ExecutionScope
import es.horm.easyadldetektplugin.model.IdentifyingEasyAdlOperation
import es.horm.easyadldetektplugin.model.StringArgument
import io.gitlab.arturbosch.detekt.rules.fqNameOrNull
import org.jetbrains.kotlin.psi.KtElement
import org.jetbrains.kotlin.psi.KtNamed
import org.jetbrains.kotlin.psi.KtReferenceExpression
import org.jetbrains.kotlin.psi.KtSimpleNameExpression
import org.jetbrains.kotlin.resolve.calls.util.getResolvedCall
import org.jetbrains.kotlin.types.typeUtil.isUnit

class HasSuffixOperation(private val suffix: StringArgument) : IdentifyingEasyAdlOperation {

    companion object {

        private val spellings = listOf("has suffix", "has postfix", "ends with")

        fun matchesTokenText(tokenText: String): Boolean = spellings.any { it.equals(tokenText, true) }
    }

    override fun identify(ktElement: KtElement, executionScope: ExecutionScope): Boolean =
        if (ktElement is KtNamed) {
            ktElement.name?.endsWith(suffix.value) ?: false
        } else false

    override fun identifyReference(ktElement: KtElement, executionScope: ExecutionScope): Boolean =
        if (ktElement is KtSimpleNameExpression) {
            val returnType = ktElement.getResolvedCall(executionScope.bindingContext)
                ?.resultingDescriptor
                ?.returnType
            // FIXME: this is not really what is supposed to be going on.
            // We need to figure out whether the type of the current component is specified and if so check accordingly
            if(returnType?.isUnit() == true) {
                ktElement.getReferencedName().endsWith(suffix.value)
            } else if(returnType != null) {
                returnType?.fqNameOrNull()
                    ?.toString()
                    ?.endsWith(suffix.value)
                    ?: false
            } else {
                ktElement.text.endsWith(suffix.value, ignoreCase = true)
            }
        } else false
}
