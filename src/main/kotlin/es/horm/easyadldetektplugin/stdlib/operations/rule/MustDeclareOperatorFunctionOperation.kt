package es.horm.easyadldetektplugin.stdlib.operations.rule

import es.horm.easyadldetektplugin.model.ExecutionScope
import es.horm.easyadldetektplugin.model.RuleEasyAdlOperation
import es.horm.easyadldetektplugin.model.StringArgument
import io.gitlab.arturbosch.detekt.rules.isOperator
import org.jetbrains.kotlin.psi.KtDeclarationContainer
import org.jetbrains.kotlin.psi.KtElement
import org.jetbrains.kotlin.psi.KtNamedFunction

class MustDeclareOperatorFunctionOperation(private val funName: StringArgument) : RuleEasyAdlOperation {

    override val errorMessage = "This component does not declare the operator function ${funName.value}"

    companion object {

        private val spellings = listOf("must declare operator function", "must declare operator fun", "must declare operator method")

        fun matchesTokenText(tokenText: String): Boolean = spellings.any { it.equals(tokenText, true) }
    }

    override fun complies(ktElement: KtElement, executionScope: ExecutionScope): Boolean =
        (ktElement as KtDeclarationContainer).declarations.filterIsInstance<KtNamedFunction>()
            .filter { it.isOperator() }.any { it.name == funName.value }
}
