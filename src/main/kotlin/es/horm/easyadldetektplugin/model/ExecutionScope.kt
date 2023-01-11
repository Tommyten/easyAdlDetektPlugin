package es.horm.easyadldetektplugin.model

import org.jetbrains.kotlin.resolve.BindingContext

data class ExecutionScope(
    val bindingContext: BindingContext,
    val architectureDescription: ArchitectureDescription
) {
    fun getComponentByName(name: String): EasyAdlComponent? = architectureDescription.getAllComponents().find { it.name == name }
}
