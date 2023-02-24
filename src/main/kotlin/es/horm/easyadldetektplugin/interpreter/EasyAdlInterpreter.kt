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

fun main() {
    val architectureDescriptionText = """
system UiLayer:
    component Route:
        has Suffix "Route"
        is annotated with "Composable"
        must reference component Screen
        must reference component ViewModel
        #may not reference component Repository
        #may not reference component DataSource
        #may not reference component DataAccessObject

    component Screen:
        has Suffix "Screen"
        is annotated with "Composable"
        may not reference component ViewModel
        # may not reference component Repository
        # may not reference component DataSource
        # may not reference component DataAccessObject

    component ViewModel:
        has suffix "ViewModel"
        is Class
        may not reference component Screen
        must reference component Repository
        may reference component UseCase

system DomainLayer:
   component UseCase:
       has postfix "UseCase"
       is Class
       must reference component Repository
       must declare exactly "1" functions
       must declare operator function "invoke"
       may not reference component Route (hidden, test)
       #may not reference component Screen
       #may not reference component ViewModel

system Datalayer:
    component Repository:
        has Suffix "Repository"
        is Interface or class
        may reference component DataSource
        may reference component DataAccessObject
        # may not reference component UseCase

    component DataSource:
        has suffix "DataSource"
        # may not reference component UseCase

    component DataAccessObject:
        has suffix "Dao"
        is Interface
        is annotated with "Dao"
        # may not reference component UseCase
    """.trimIndent()

    val archDesc = interpretArchitectureDescription(architectureDescriptionText)
    println(archDesc)
}


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

    private val loadedOperatorFactories = ServiceLoader.load(OperationFactory::class.java, OperationFactory::class.java.classLoader)

    override fun visitOperation(ctx: OperationContext): EasyAdlOperation {
        val operator = ctx.operator().joinToString(separator = " [ARG] ") { it.ID().joinToString(separator = " ") { it.text } }

        val suitableFactory = try {
            loadedOperatorFactories.single { it.canCreateOperation(operator) }
        } catch (noElement: NoSuchElementException) {
            error("No Operator Factory was loaded which provides a definition for operator \"$operator\"")
        } catch (tooMany: IllegalArgumentException) {
            error("The operator \"$operator\" was found in multiple operator factories")
        }

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
        val modifiers = ctx.modifiers()?.let { modifier -> modifier.ID().map { it.text } } ?: listOf()

        val operation = suitableFactory.createOperation(operator, argumentList, modifiers)
        if (operation is IdentifyingEasyAdlOperation) {
            require(argumentList.all { it !is ComponentArgument }) {
                "Identifying Operations are currently not supported with component Arguments."
            }
        }

        return operation
    }
}


