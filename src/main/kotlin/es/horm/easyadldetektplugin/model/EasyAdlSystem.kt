package es.horm.easyadldetektplugin.model

data class EasyAdlSystem(
    val name: String,
    val easyAdlComponents: List<EasyAdlComponent>
) : ArchitectureFragment
