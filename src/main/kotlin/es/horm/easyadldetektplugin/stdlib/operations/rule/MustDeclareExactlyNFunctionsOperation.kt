package es.horm.easyadldetektplugin.stdlib.operations.rule

import es.horm.easyadldetektplugin.model.ExecutionScope
import es.horm.easyadldetektplugin.model.RuleEasyAdlOperation
import es.horm.easyadldetektplugin.model.StringArgument
import org.jetbrains.kotlin.psi.KtDeclarationContainer
import org.jetbrains.kotlin.psi.KtElement
import org.jetbrains.kotlin.psi.KtFunction

class MustDeclareExactlyNFunctionsOperation(amountString: StringArgument) : RuleEasyAdlOperation {

    val amount: Int = amountString.value.toInt()

    override val errorMessage: String = "This component does not declare exactly ${amountString.value} functions."

    companion object {

        private val spellings = listOf("must declare exactly [ARG] functions")

        fun matchesTokenText(tokenText: String): Boolean = spellings.any { it.equals(tokenText, true) }
    }

    override fun complies(ktElement: KtElement, executionScope: ExecutionScope): Boolean =
        (ktElement as? KtDeclarationContainer)?.declarations?.filterIsInstance<KtFunction>()?.count() == amount
}
