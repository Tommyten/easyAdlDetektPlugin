package es.horm.easyadldetektplugin.operations

import es.horm.easyadldetektplugin.model.IdentifyingOperation
import es.horm.easyadldetektplugin.model.Operation
import es.horm.easyadldetektplugin.model.OperationFactory
import es.horm.easyadldetektplugin.model.RuleOperation
import org.jetbrains.kotlin.psi.KtClass
import org.jetbrains.kotlin.psi.KtElement
import org.jetbrains.kotlin.psi.KtNamedDeclaration
import org.jetbrains.kotlin.psi.psiUtil.getChildrenOfType

class TestOperationFactory : OperationFactory {
    override fun createOperation(tokenText: String, arguments: List<String>): Operation {
        if (HasSuffixOperation.matchesTokenText(tokenText)) {
            return HasSuffixOperation(arguments.single())
        } else if(IsClassOperation.matchesTokenText(tokenText)) {
            require(arguments.isEmpty())
            return IsClassOperation()
        } else if(IsNotInnerClassOperation.matchesTokenText(tokenText)) {
            require(arguments.isEmpty())
            return IsNotInnerClassOperation()
        } else if(MustHaveInnerClassOperation.matchesTokenText(tokenText)) {
            require(arguments.isEmpty())
            return MustHaveInnerClassOperation()
        }
        throw IllegalStateException()
    }

    override fun canCreateOperation(tokenText: String): Boolean {
        return HasSuffixOperation.matchesTokenText(tokenText) ||
                IsClassOperation.matchesTokenText(tokenText) ||
                IsNotInnerClassOperation.matchesTokenText(tokenText) ||
                MustHaveInnerClassOperation.matchesTokenText(tokenText)
    }
}

class MustHaveInnerClassOperation : RuleOperation {

    companion object {

        val spellings = listOf("must have inner class")

        fun matchesTokenText(tokenText: String): Boolean = spellings.any { it.equals(tokenText, true) }
    }

    override fun complies(ktElement: KtElement): Boolean {
        if(ktElement is KtClass) {
            val childClasses = ktElement.body?.getChildrenOfType<KtClass>()
            if(childClasses.isNullOrEmpty()) return false
            return childClasses.any { it.isInner() }
        }
        return false
    }

}

class HasSuffixOperation(val suffix: String) : IdentifyingOperation {

    companion object {

        val spellings = listOf("has suffix", "has postfix", "ends with")

        fun matchesTokenText(tokenText: String): Boolean = spellings.any { it.equals(tokenText, true) }
    }

    override fun identify(ktElement: KtElement): Boolean =
        if (ktElement is KtNamedDeclaration) {
            ktElement.name?.endsWith(suffix) ?: false
        } else false
}

class IsClassOperation : IdentifyingOperation {

    companion object {

        val spellings = listOf("is class")

        fun matchesTokenText(tokenText: String): Boolean = spellings.any { it.equals(tokenText, true) }
    }

    override fun identify(ktElement: KtElement): Boolean = ktElement is KtClass
}

class IsNotInnerClassOperation : IdentifyingOperation {

    companion object {

        val spellings = listOf("is not inner class")

        fun matchesTokenText(tokenText: String): Boolean = spellings.any { it.equals(tokenText, true) }
    }

    override fun identify(ktElement: KtElement): Boolean = ktElement is KtClass && !ktElement.isInner()
}

