package es.horm.easyadldetektplugin.interpreter

import es.horm.easyadldetektplugin.lang.EasyAdlBaseVisitor
import es.horm.easyadldetektplugin.lang.EasyAdlLexer
import es.horm.easyadldetektplugin.lang.EasyAdlParser
import es.horm.easyadldetektplugin.lang.EasyAdlParser.ArchitectureDescriptionContext
import es.horm.easyadldetektplugin.lang.EasyAdlParser.ComponentDefinitionContext
import es.horm.easyadldetektplugin.lang.EasyAdlParser.OperationContext
import es.horm.easyadldetektplugin.lang.EasyAdlParser.SystemDefinitionContext
import es.horm.easyadldetektplugin.model.ArchitectureDescription
import es.horm.easyadldetektplugin.model.ArchitectureFragment
import es.horm.easyadldetektplugin.model.Argument
import es.horm.easyadldetektplugin.model.ComponentArgument
import es.horm.easyadldetektplugin.model.EasyAdlComponent
import es.horm.easyadldetektplugin.model.EasyAdlOperation
import es.horm.easyadldetektplugin.model.EasyAdlSystem
import es.horm.easyadldetektplugin.model.IdentifyingEasyAdlOperation
import es.horm.easyadldetektplugin.model.OperationFactory
import es.horm.easyadldetektplugin.model.StringArgument
import org.antlr.v4.runtime.CharStreams
import org.antlr.v4.runtime.CommonTokenStream
import java.util.*
import kotlin.NoSuchElementException

fun interpretArchitectureDescription(
    architectureDescriptionText: String
): ArchitectureDescription {
    val lexer = EasyAdlLexer(CharStreams.fromString(architectureDescriptionText))
    val parser = EasyAdlParser(CommonTokenStream(lexer))

    return parser.architectureDescription().accept(ArchitectureDescriptionVisitor())
}

class ArchitectureDescriptionVisitor : EasyAdlBaseVisitor<ArchitectureDescription>() {

    override fun visitArchitectureDescription(ctx: ArchitectureDescriptionContext): ArchitectureDescription {
        val fragmentsInArchitecture = mutableListOf<ArchitectureFragment>()
        fragmentsInArchitecture.addAll(ctx.componentDefinition().map { it.accept(ComponentVisitor()) })
        fragmentsInArchitecture.addAll(ctx.systemDefinition().map { it.accept(SystemVisitor()) })
        return ArchitectureDescription(fragmentsInArchitecture)
    }
}

class SystemVisitor : EasyAdlBaseVisitor<EasyAdlSystem>() {
    override fun visitSystemDefinition(ctx: SystemDefinitionContext): EasyAdlSystem {
        val systemIdentifier = ctx.system().ID().text
        val components = ctx.componentDefinition().map { it.accept(ComponentVisitor()) }
        return EasyAdlSystem(systemIdentifier, components)
    }
}

class ComponentVisitor : EasyAdlBaseVisitor<EasyAdlComponent>() {
    override fun visitComponentDefinition(ctx: ComponentDefinitionContext): EasyAdlComponent {
        val componentIdentifier = ctx.component().ID().text
        val operators = ctx.operation().map { it.accept(OperationVisitor()) }
        return EasyAdlComponent(componentIdentifier, operators)
    }
}

class OperationVisitor : EasyAdlBaseVisitor<EasyAdlOperation>() {

    private val loadedOperatorFactories = ServiceLoader.load(OperationFactory::class.java)

    override fun visitOperation(ctx: OperationContext): EasyAdlOperation {
        val argumentList = mutableListOf<Argument>()
        argumentList.addAll(
            ctx.argument()
                .mapNotNull { it.ARGUMENT()?.text?.removeSurrounding("\"") }
                .map { StringArgument(it) }
        )
        argumentList.addAll(
            ctx.argument()
                .mapNotNull { it.component()?.ID()?.text }
                .map { ComponentArgument(it) }
        )
        val operator = ctx.operator().ID().joinToString(" ") { it.text }

        val suitableFactory = try {
            loadedOperatorFactories.single { it.canCreateOperation(operator) }
        } catch (noElement: NoSuchElementException) {
            error("No Operator Factory was loaded which provides a definition for operator \"$operator\"")
        } catch (tooMany: IllegalArgumentException) {
            error("The operator \"$operator\" was found in multiple operator factories")
        }

        val operation = suitableFactory.createOperation(operator, argumentList)
        if (operation is IdentifyingEasyAdlOperation) {
            require(argumentList.all { it !is ComponentArgument }) {
                "Identifying Operations are currently not supported with component Arguments."
            }
        }

        return operation
    }
}


