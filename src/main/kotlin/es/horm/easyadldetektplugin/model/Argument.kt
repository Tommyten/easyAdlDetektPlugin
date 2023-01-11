package es.horm.easyadldetektplugin.model

sealed interface Argument

@JvmInline
value class StringArgument(val value: String) : Argument

data class ComponentArgument(
    val componentName: String
): Argument
