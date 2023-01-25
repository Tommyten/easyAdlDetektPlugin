package es.horm.easyadldetektplugin.processor

import es.horm.easyadldetektplugin.config.EasyAdlArchitectureHolder
import es.horm.easyadldetektplugin.config.EasyAdlArchitectureReader
import es.horm.easyadldetektplugin.interpreter.interpretArchitectureDescription
import es.horm.easyadldetektplugin.model.ArchitectureDescription
import es.horm.easyadldetektplugin.model.EasyAdlComponent
import es.horm.easyadldetektplugin.model.ExecutionScope
import io.gitlab.arturbosch.detekt.api.DetektVisitor
import io.gitlab.arturbosch.detekt.api.Detektion
import io.gitlab.arturbosch.detekt.api.Entity
import io.gitlab.arturbosch.detekt.api.FileProcessListener
import org.jetbrains.kotlin.com.intellij.openapi.util.Key
import org.jetbrains.kotlin.psi.KtElement
import org.jetbrains.kotlin.psi.KtFile
import org.jetbrains.kotlin.resolve.BindingContext

class ArchComponentProcessor : FileProcessListener {

    private var architectureDescription: ArchitectureDescription = EasyAdlArchitectureHolder.architectureDescription ?: throw IllegalStateException()

    var architectureDescriptionText: String = ""
        set(value) {
            field = value
            architectureDescription = interpretArchitectureDescription(architectureDescriptionText)
        }

    override fun onProcess(file: KtFile, bindingContext: BindingContext) {
        if (bindingContext == BindingContext.EMPTY) return
        val visitor = ArchitectureComponentIdentifierVisitor(bindingContext, architectureDescription)
        file.accept(visitor)
        file.putUserData(identifiedComponentsKey, visitor.identifiedComponentsMap)
    }

    override fun onFinish(files: List<KtFile>, result: Detektion, bindingContext: BindingContext) {
        val test = files
                .map { it.getUserData(identifiedComponentsKey) ?: mapOf() }
                .flatMap { it.entries }
                .groupBy { it.key }
                .map { it.key to it.value.flatMap { it.value } }
                .associate { it.first to it.second }
        result.addData(identifiedComponentsKey, test)
    }

    companion object {
        val identifiedComponentsKey = Key<Map<EasyAdlComponent, List<Entity>>>("identified components key")
    }

    class ArchitectureComponentIdentifierVisitor(
        private val bindingContext: BindingContext,
        private val architectureDescription: ArchitectureDescription
    ) : DetektVisitor() {

        internal val identifiedComponentsMap = mutableMapOf<EasyAdlComponent, MutableList<Entity>>()

        override fun visitKtElement(element: KtElement) {

            val executionScope = ExecutionScope(bindingContext, architectureDescription)
            val easyAdlComponents = architectureDescription.getAllComponents()

            easyAdlComponents.forEach { component ->
                if (component.canComponentBeIdentified(element, executionScope)) {
                    identifiedComponentsMap.getOrPut(component) { mutableListOf() }.add(Entity.from(element))
                }
            }

            super.visitKtElement(element)

        }
    }
}
