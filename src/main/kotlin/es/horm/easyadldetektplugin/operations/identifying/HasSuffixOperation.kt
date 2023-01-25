package es.horm.easyadldetektplugin.operations.identifying

import es.horm.easyadldetektplugin.model.ExecutionScope
import es.horm.easyadldetektplugin.model.IdentifyingEasyAdlOperation
import es.horm.easyadldetektplugin.model.StringArgument
import io.gitlab.arturbosch.detekt.rules.fqNameOrNull
import org.jetbrains.kotlin.psi.KtElement
import org.jetbrains.kotlin.psi.KtNamed
import org.jetbrains.kotlin.psi.KtSimpleNameExpression
import org.jetbrains.kotlin.resolve.calls.util.getResolvedCall

class HasSuffixOperation(private val suffix: StringArgument) : IdentifyingEasyAdlOperation {

    companion object {

        private val spellings = listOf("has suffix", "has postfix", "ends with")

        fun matchesTokenText(tokenText: String): Boolean = spellings.any { it.equals(tokenText, true) }
    }

    override fun identify(ktElement: KtElement, executionScope: ExecutionScope): Boolean =
        if (ktElement is KtNamed) {
            ktElement.name?.endsWith(suffix.value) ?: false
        } else false

    override fun identifyReference(ktElement: KtElement, executionScope: ExecutionScope): Boolean? =
        if (ktElement is KtSimpleNameExpression) {
            ktElement.getResolvedCall(executionScope.bindingContext)
                ?.resultingDescriptor
                ?.returnType
                ?.fqNameOrNull()
                ?.toString()
                ?.endsWith(suffix.value)
                ?: false
        } else false
}
