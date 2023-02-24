package es.horm.easyadldetektplugin.stdlib.operations.rule

import es.horm.easyadldetektplugin.model.ExecutionScope
import es.horm.easyadldetektplugin.model.RuleEasyAdlOperation
import io.gitlab.arturbosch.detekt.rules.isInternal
import org.jetbrains.kotlin.psi.KtElement
import org.jetbrains.kotlin.psi.KtModifierListOwner

class MustBeInternalOperation : RuleEasyAdlOperation{

    companion object {

        private val spellings = listOf("must be internal")

        fun matchesTokenText(tokenText: String): Boolean = spellings.any { it.equals(tokenText, true) }
    }

    override val errorMessage: String = "This component is not declared as internal"

    override fun complies(ktElement: KtElement, executionScope: ExecutionScope): Boolean =
        (ktElement as? KtModifierListOwner)?.isInternal() ?: false
}
