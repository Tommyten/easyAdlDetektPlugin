package es.horm.easyadldetektplugin.interpreter

import es.horm.easyadldetektplugin.lang.EasyAdlBaseVisitor
import es.horm.easyadldetektplugin.lang.EasyAdlLexer
import es.horm.easyadldetektplugin.lang.EasyAdlParser
import es.horm.easyadldetektplugin.model.ArchitectureDescription
import es.horm.easyadldetektplugin.model.ArchitectureFragment
import es.horm.easyadldetektplugin.model.Component
import es.horm.easyadldetektplugin.model.Operation
import es.horm.easyadldetektplugin.model.OperationFactory
import es.horm.easyadldetektplugin.model.System
import org.antlr.v4.runtime.CharStreams
import org.antlr.v4.runtime.CommonTokenStream
import java.util.*

class EasyAdlInterpreter {

    fun interpretArchitectureDescription(architectureDescriptionText: String): ArchitectureDescription {
        val lexer = EasyAdlLexer(CharStreams.fromString(architectureDescriptionText))
        val parser = EasyAdlParser(CommonTokenStream(lexer))

        val arch = parser.architectureDescription().accept(ArchitectureDescriptionVisitor())

        println(arch)

        return arch
    }

}

class ArchitectureDescriptionVisitor : EasyAdlBaseVisitor<ArchitectureDescription>() {

    override fun visitArchitectureDescription(ctx: EasyAdlParser.ArchitectureDescriptionContext): ArchitectureDescription {
        val fragmentsInArchitecture = mutableListOf<ArchitectureFragment>()
        fragmentsInArchitecture.addAll(ctx.componentDefinition().map { it.accept(ComponentVisitor()) })
        fragmentsInArchitecture.addAll(ctx.systemDefinition().map { it.accept(SystemVisitor()) })
        return ArchitectureDescription(fragmentsInArchitecture)
    }
}

class SystemVisitor : EasyAdlBaseVisitor<System>() {
    override fun visitSystemDefinition(ctx: EasyAdlParser.SystemDefinitionContext): System {
        val systemIdentifier = ctx.system().ID().text
        val components = ctx.componentDefinition().map { it.accept(ComponentVisitor()) }
        return System(systemIdentifier, components)
    }
}

class ComponentVisitor : EasyAdlBaseVisitor<Component>() {
    override fun visitComponentDefinition(ctx: EasyAdlParser.ComponentDefinitionContext): Component {
        val componentIdentifier = ctx.component().ID().text
        val operators = ctx.operation().map { it.accept(OperatorVisitor()) }
        return Component(componentIdentifier, operators)
    }
}

class OperatorVisitor : EasyAdlBaseVisitor<Operation>() {

    private val loadedOperatorFactories = ServiceLoader.load(OperationFactory::class.java)

    override fun visitOperation(ctx: EasyAdlParser.OperationContext): Operation {
        val arguments = ctx.argument().map { it.text.removeSurrounding("\"") }
        val operator = ctx.operator().ID().joinToString(" ") { it.text }

        val suitableFactory = try {
             loadedOperatorFactories.single { it.canCreateOperation(operator) }
        } catch (noElement: NoSuchElementException) {
            error("No Operator Factory was loaded which provides a definition for operator \"$operator\"")
        } catch (tooMany: IllegalArgumentException) {
            error("The operator \"$operator\" was found in multiple operator factories")
        }

        return suitableFactory.createOperation(operator, arguments)
    }
}
