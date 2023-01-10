package es.horm.easyadldetektplugin.model

data class System(
    val name: String,
    val components: List<Component>
) : ArchitectureFragment
