package es.horm.easyadldetektplugin.operations

import es.horm.easyadldetektplugin.model.Argument
import es.horm.easyadldetektplugin.model.ComponentArgument
import es.horm.easyadldetektplugin.model.EasyAdlOperation
import es.horm.easyadldetektplugin.model.OperationFactory
import es.horm.easyadldetektplugin.model.StringArgument
import es.horm.easyadldetektplugin.operations.identifying.HasSuffixOperation
import es.horm.easyadldetektplugin.operations.identifying.IsAnnotatedWithOperation
import es.horm.easyadldetektplugin.operations.identifying.IsClassOperation
import es.horm.easyadldetektplugin.operations.identifying.IsNotInnerClassOperation
import es.horm.easyadldetektplugin.operations.rule.MustDeclarePublicMethodOperation
import es.horm.easyadldetektplugin.operations.rule.MayNotReferenceOperation
import es.horm.easyadldetektplugin.operations.rule.MustHaveInnerClassOperation
import es.horm.easyadldetektplugin.operations.rule.MustReferenceOperation

class CoreOperationFactory : OperationFactory {
    override fun createOperation(tokenText: String, arguments: List<Argument>): EasyAdlOperation {
        if (HasSuffixOperation.matchesTokenText(tokenText)) {
            require(arguments.all { it is StringArgument }) { "$tokenText requires a single String argument" }
            require(arguments.size == 1) { "$tokenText requires a single String argument" }
            return HasSuffixOperation(arguments.single() as StringArgument)
        } else if (IsClassOperation.matchesTokenText(tokenText)) {
            require(arguments.isEmpty()) { "$tokenText does not allow arguments" }
            return IsClassOperation()
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
            return MayNotReferenceOperation(arguments.single() as ComponentArgument)
        } else if (MustDeclarePublicMethodOperation.matchesTokenText(tokenText)) {
            require(arguments.single() is StringArgument)
            return MustDeclarePublicMethodOperation(arguments.single() as StringArgument)
        }
        throw IllegalStateException()
    }

    override fun canCreateOperation(tokenText: String): Boolean =
        HasSuffixOperation.matchesTokenText(tokenText) ||
                IsClassOperation.matchesTokenText(tokenText) ||
                IsNotInnerClassOperation.matchesTokenText(tokenText) ||
                MustHaveInnerClassOperation.matchesTokenText(tokenText) ||
                MustReferenceOperation.matchesTokenText(tokenText) ||
                MayNotReferenceOperation.matchesTokenText(tokenText) ||
                IsAnnotatedWithOperation.matchesTokenText(tokenText) ||
                MustDeclarePublicMethodOperation.matchesTokenText(tokenText)
}










