package es.horm.easyadldetektplugin.operations

import es.horm.easyadldetektplugin.model.Argument
import es.horm.easyadldetektplugin.model.ComponentArgument
import es.horm.easyadldetektplugin.model.EasyAdlOperation
import es.horm.easyadldetektplugin.model.ExecutionScope
import es.horm.easyadldetektplugin.model.IdentifyingEasyAdlOperation
import es.horm.easyadldetektplugin.model.OperationFactory
import es.horm.easyadldetektplugin.model.RuleEasyAdlOperation
import es.horm.easyadldetektplugin.model.StringArgument
import io.gitlab.arturbosch.detekt.rules.fqNameOrNull
import org.jetbrains.kotlin.psi.KtClass
import org.jetbrains.kotlin.psi.KtElement
import org.jetbrains.kotlin.psi.KtNameReferenceExpression
import org.jetbrains.kotlin.psi.KtNamedDeclaration
import org.jetbrains.kotlin.psi.KtNamedFunction
import org.jetbrains.kotlin.psi.KtReferenceExpression
import org.jetbrains.kotlin.psi.psiUtil.collectDescendantsOfType
import org.jetbrains.kotlin.psi.psiUtil.getChildrenOfType
import org.jetbrains.kotlin.resolve.calls.util.getResolvedCall

class TestOperationFactory : OperationFactory {
    override fun createOperation(tokenText: String, arguments: List<Argument>): EasyAdlOperation {
        if (HasSuffixEasyAdlOperation.matchesTokenText(tokenText)) {
            require(arguments.all { it is StringArgument }) { "$tokenText requires a single String argument" }
            require(arguments.size == 1) { "$tokenText requires a single String argument" }
            return HasSuffixEasyAdlOperation(arguments.single() as StringArgument)
        } else if (IsClassEasyAdlOperation.matchesTokenText(tokenText)) {
            require(arguments.isEmpty()) { "$tokenText does not allow arguments" }
            return IsClassEasyAdlOperation()
        } else if (IsAnnotatedWithOperation.matchesTokenText(tokenText)) {
            require(arguments.all { it is StringArgument }) { "$tokenText requires a single String argument" }
            require(arguments.size == 1) { "$tokenText requires a single String argument" }
            return IsAnnotatedWithOperation(arguments.single() as StringArgument)
        } else if (IsNotInnerClassEasyAdlOperation.matchesTokenText(tokenText)) {
            require(arguments.isEmpty()) { "$tokenText does not allow arguments" }
            return IsNotInnerClassEasyAdlOperation()
        } else if (MustHaveInnerClassEasyAdlOperation.matchesTokenText(tokenText)) {
            require(arguments.isEmpty()) { "$tokenText does not allow arguments" }
            return MustHaveInnerClassEasyAdlOperation()
        } else if (MustReferenceOperation.matchesTokenText(tokenText)) {
            require(arguments.single() is ComponentArgument)
            return MustReferenceOperation(arguments.single() as ComponentArgument)
        } else if (MayNotReferenceOperation.matchesTokenText(tokenText)) {
            require(arguments.single() is ComponentArgument)
            return MayNotReferenceOperation(arguments.single() as ComponentArgument)
        }
        throw IllegalStateException()
    }

    override fun canCreateOperation(tokenText: String): Boolean =
        HasSuffixEasyAdlOperation.matchesTokenText(tokenText) ||
                IsClassEasyAdlOperation.matchesTokenText(tokenText) ||
                IsNotInnerClassEasyAdlOperation.matchesTokenText(tokenText) ||
                MustHaveInnerClassEasyAdlOperation.matchesTokenText(tokenText) ||
                MustReferenceOperation.matchesTokenText(tokenText) ||
                MayNotReferenceOperation.matchesTokenText(tokenText) ||
                IsAnnotatedWithOperation.matchesTokenText(tokenText)
}

class MustHaveInnerClassEasyAdlOperation : RuleEasyAdlOperation {

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

class HasSuffixEasyAdlOperation(private val suffix: StringArgument) : IdentifyingEasyAdlOperation {

    companion object {

        private val spellings = listOf("has suffix", "has postfix", "ends with")

        fun matchesTokenText(tokenText: String): Boolean = spellings.any { it.equals(tokenText, true) }
    }

    override fun identify(ktElement: KtElement, executionScope: ExecutionScope): Boolean =
        if (ktElement is KtNamedDeclaration) {
            ktElement.name?.endsWith(suffix.value) ?: false
        } else false

    override fun identifyReference(ktElement: KtElement, executionScope: ExecutionScope): Boolean =
        if (ktElement is KtNameReferenceExpression) {
            ktElement.getResolvedCall(executionScope.bindingContext)
                ?.resultingDescriptor
                ?.returnType
                ?.fqNameOrNull()
                ?.toString()
                ?.endsWith(suffix.value)
                ?: false
        } else false
}

class IsClassEasyAdlOperation : IdentifyingEasyAdlOperation {

    companion object {

        private val spellings = listOf("is class")

        fun matchesTokenText(tokenText: String): Boolean = spellings.any { it.equals(tokenText, true) }
    }

    override fun identify(ktElement: KtElement, executionScope: ExecutionScope): Boolean = ktElement is KtClass

    override fun identifyReference(ktElement: KtElement, executionScope: ExecutionScope): Boolean = true
}

class IsNotInnerClassEasyAdlOperation : IdentifyingEasyAdlOperation {

    companion object {

        private val spellings = listOf("is not inner class")

        fun matchesTokenText(tokenText: String): Boolean = spellings.any { it.equals(tokenText, true) }
    }

    override fun identify(ktElement: KtElement, executionScope: ExecutionScope): Boolean =
        ktElement is KtClass && !ktElement.isInner()

    override fun identifyReference(ktElement: KtElement, executionScope: ExecutionScope): Boolean = true
}

class MustReferenceOperation(private val componentArgument: ComponentArgument) : RuleEasyAdlOperation {

    companion object {

        private val spellings = listOf("must reference")

        fun matchesTokenText(tokenText: String): Boolean = spellings.any { it.equals(tokenText, true) }
    }

    override fun complies(ktElement: KtElement, executionScope: ExecutionScope): Boolean {
        val referencedComponent = executionScope.getComponentByName(componentArgument.componentName) ?: return false
        val referenceExpressions = ktElement.collectDescendantsOfType<KtReferenceExpression>()
        val identificationOperationsOfReferenced =
            referencedComponent.easyAdlOperations.filterIsInstance<IdentifyingEasyAdlOperation>()

        return referenceExpressions.any { refExpr ->
            identificationOperationsOfReferenced.any { idOp ->
                idOp.identifyReference(refExpr, executionScope)
            }
        }
    }
}

class MayNotReferenceOperation(private val componentArgument: ComponentArgument) : RuleEasyAdlOperation {

    companion object {

        private val spellings = listOf("may not reference")

        fun matchesTokenText(tokenText: String): Boolean = spellings.any { it.equals(tokenText, true) }
    }

    override fun complies(ktElement: KtElement, executionScope: ExecutionScope): Boolean {
        val referencedComponent = executionScope.getComponentByName(componentArgument.componentName) ?: return false
        val referenceExpressions = ktElement.collectDescendantsOfType<KtReferenceExpression>()
        val identificationOperationsOfReferenced =
            referencedComponent.easyAdlOperations.filterIsInstance<IdentifyingEasyAdlOperation>()

        return referenceExpressions.none { refExpr ->
            identificationOperationsOfReferenced.any { idOp ->
                idOp.identifyReference(refExpr, executionScope)
            }
        }
    }
}

class IsAnnotatedWithOperation(private val stringArgument: StringArgument) : IdentifyingEasyAdlOperation {

    companion object {

        private val spellings = listOf("is annotated with")

        fun matchesTokenText(tokenText: String): Boolean = spellings.any { it.equals(tokenText, true) }
    }

    override fun identify(ktElement: KtElement, executionScope: ExecutionScope): Boolean =
        if(ktElement is KtNamedFunction) {
            ktElement.annotationEntries.any { it.shortName?.toString()?.equals(stringArgument.value) ?: false }
        } else false
        //(element as KtNamedFunction).annotationEntries.first().shortName


    override fun identifyReference(ktElement: KtElement, executionScope: ExecutionScope): Boolean =
        if(ktElement is KtNameReferenceExpression) {
            ktElement.getResolvedCall(executionScope.bindingContext)?.resultingDescriptor?.annotations?.any { annotation ->
                annotation.fqName.toString().endsWith(stringArgument.value)
            } ?: false
        } else false


}
