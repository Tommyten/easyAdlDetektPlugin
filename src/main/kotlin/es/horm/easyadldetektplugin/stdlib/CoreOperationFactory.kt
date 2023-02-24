package es.horm.easyadldetektplugin.stdlib

import es.horm.easyadldetektplugin.model.Argument
import es.horm.easyadldetektplugin.model.ComponentArgument
import es.horm.easyadldetektplugin.model.EasyAdlOperation
import es.horm.easyadldetektplugin.model.OperationFactory
import es.horm.easyadldetektplugin.model.StringArgument
import es.horm.easyadldetektplugin.stdlib.operations.identifying.HasSuffixOperation
import es.horm.easyadldetektplugin.stdlib.operations.identifying.IsAnnotatedWithOperation
import es.horm.easyadldetektplugin.stdlib.operations.identifying.IsClassOperation
import es.horm.easyadldetektplugin.stdlib.operations.identifying.IsInterfaceOperation
import es.horm.easyadldetektplugin.stdlib.operations.identifying.IsInterfaceOrClassOperation
import es.horm.easyadldetektplugin.stdlib.operations.identifying.IsInternalOperation
import es.horm.easyadldetektplugin.stdlib.operations.identifying.IsNotInnerClassOperation
import es.horm.easyadldetektplugin.stdlib.operations.identifying.MatchesRegexOperation
import es.horm.easyadldetektplugin.stdlib.operations.rule.MustDeclarePublicMethodOperation
import es.horm.easyadldetektplugin.stdlib.operations.rule.MayNotReferenceOperation
import es.horm.easyadldetektplugin.stdlib.operations.rule.MayReferenceOperation
import es.horm.easyadldetektplugin.stdlib.operations.rule.MustBeInternalOperation
import es.horm.easyadldetektplugin.stdlib.operations.rule.MustDeclareExactlyNFunctionsOperation
import es.horm.easyadldetektplugin.stdlib.operations.rule.MustDeclareOperatorFunctionOperation
import es.horm.easyadldetektplugin.stdlib.operations.rule.MustHaveInnerClassOperation
import es.horm.easyadldetektplugin.stdlib.operations.rule.MustReferenceOperation

class CoreOperationFactory : OperationFactory {
    override fun createOperation(tokenText: String, arguments: List<Argument>, modifiers: List<String>): EasyAdlOperation {
        if (HasSuffixOperation.matchesTokenText(tokenText)) {
            require(arguments.all { it is StringArgument }) { "$tokenText requires a single String argument" }
            require(arguments.size == 1) { "$tokenText requires a single String argument" }
            return HasSuffixOperation(arguments.single() as StringArgument)
        } else if (MatchesRegexOperation.matchesTokenText(tokenText)) {
            require(arguments.all { it is StringArgument }) { "$tokenText requires a single String argument" }
            require(arguments.size == 1) { "$tokenText requires a single String argument" }
            return MatchesRegexOperation(arguments.single() as StringArgument)
        } else if (IsClassOperation.matchesTokenText(tokenText)) {
            require(arguments.isEmpty()) { "$tokenText does not allow arguments" }
            return IsClassOperation()
        } else if (IsInterfaceOperation.matchesTokenText(tokenText)) {
            require(arguments.isEmpty()) { "$tokenText does not allow arguments" }
            return IsInterfaceOperation()
        } else if (IsInterfaceOrClassOperation.matchesTokenText(tokenText)) {
            require(arguments.isEmpty()) { "$tokenText does not allow arguments" }
            return IsInterfaceOrClassOperation()
        } else if (IsAnnotatedWithOperation.matchesTokenText(tokenText)) {
            require(arguments.all { it is StringArgument }) { "$tokenText requires a single String argument" }
            require(arguments.size == 1) { "$tokenText requires a single String argument" }
            return IsAnnotatedWithOperation(arguments.single() as StringArgument)
        } else if (IsNotInnerClassOperation.matchesTokenText(tokenText)) {
            require(arguments.isEmpty()) { "$tokenText does not allow arguments" }
            return IsNotInnerClassOperation()
        } else if (MustHaveInnerClassOperation.matchesTokenText(tokenText)) {
            require(arguments.isEmpty()) { "$tokenText does not allow arguments" }
            return MustHaveInnerClassOperation()
        } else if (MustReferenceOperation.matchesTokenText(tokenText)) {
            require(arguments.single() is ComponentArgument)
            return MustReferenceOperation(arguments.single() as ComponentArgument)
        } else if (MayNotReferenceOperation.matchesTokenText(tokenText)) {
            require(arguments.single() is ComponentArgument)
            return MayNotReferenceOperation(arguments.single() as ComponentArgument, modifiers)
        } else if (MustDeclarePublicMethodOperation.matchesTokenText(tokenText)) {
            require(arguments.single() is StringArgument)
            return MustDeclarePublicMethodOperation(arguments.single() as StringArgument)
        } else if (MustDeclareExactlyNFunctionsOperation.matchesTokenText(tokenText)) {
            require(arguments.single() is StringArgument)
            return MustDeclareExactlyNFunctionsOperation(arguments.single() as StringArgument)
        } else if (MustDeclareOperatorFunctionOperation.matchesTokenText(tokenText)) {
            require(arguments.single() is StringArgument)
            return MustDeclareOperatorFunctionOperation(arguments.single() as StringArgument)
        } else if (MayReferenceOperation.matchesTokenText(tokenText)) {
            require(arguments.single() is ComponentArgument)
            return MayReferenceOperation(arguments.single() as ComponentArgument)
        } else if (MustBeInternalOperation.matchesTokenText(tokenText)) {
            require(arguments.isEmpty()) { "$tokenText does not allow arguments" }
            return MustBeInternalOperation()
        } else if (IsInternalOperation.matchesTokenText(tokenText)) {
            require(arguments.isEmpty()) { "$tokenText does not allow arguments" }
            return IsInternalOperation()
        }
        throw IllegalStateException()
    }

    override fun canCreateOperation(tokenText: String): Boolean =
        HasSuffixOperation.matchesTokenText(tokenText) ||
                IsClassOperation.matchesTokenText(tokenText) ||
                IsInterfaceOperation.matchesTokenText(tokenText) ||
                IsNotInnerClassOperation.matchesTokenText(tokenText) ||
                MatchesRegexOperation.matchesTokenText(tokenText) ||
                MustHaveInnerClassOperation.matchesTokenText(tokenText) ||
                MustReferenceOperation.matchesTokenText(tokenText) ||
                MayNotReferenceOperation.matchesTokenText(tokenText) ||
                IsAnnotatedWithOperation.matchesTokenText(tokenText) ||
                MustDeclarePublicMethodOperation.matchesTokenText(tokenText) ||
                MustDeclareExactlyNFunctionsOperation.matchesTokenText(tokenText) ||
                MustDeclareOperatorFunctionOperation.matchesTokenText(tokenText) ||
                MayReferenceOperation.matchesTokenText(tokenText) ||
                IsInterfaceOrClassOperation.matchesTokenText(tokenText) ||
                IsInternalOperation.matchesTokenText(tokenText) ||
                MustBeInternalOperation.matchesTokenText(tokenText)
}
