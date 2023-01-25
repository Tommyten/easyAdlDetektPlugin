package es.horm.easyadldetektplugin.operations.rule

import es.horm.easyadldetektplugin.model.ExecutionScope
import es.horm.easyadldetektplugin.model.RuleEasyAdlOperation
import org.jetbrains.kotlin.psi.KtClass
import org.jetbrains.kotlin.psi.KtElement
import org.jetbrains.kotlin.psi.psiUtil.getChildrenOfType

class MustHaveInnerClassOperation : RuleEasyAdlOperation {

    companion object {

        private val spellings = listOf("must have inner class")

        fun matchesTokenText(tokenText: String): Boolean = spellings.any { it.equals(tokenText, true) }
    }

    override fun complies(ktElement: KtElement, executionScope: ExecutionScope): Boolean {
        if (ktElement is KtClass) {
            val childClasses = ktElement.body?.getChildrenOfType<KtClass>()
            if (childClasses.isNullOrEmpty()) return false
            return childClasses.any { it.isInner() }
        }
        return false
    }

}
