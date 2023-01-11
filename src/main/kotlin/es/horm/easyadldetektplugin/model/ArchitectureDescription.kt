package es.horm.easyadldetektplugin.model

data class ArchitectureDescription(
    val architectureFragments: List<ArchitectureFragment>
) {
    fun getAllComponents(): List<EasyAdlComponent> {
        val freeComponents = architectureFragments.filterIsInstance<EasyAdlComponent>()
        val componentsInSystems =
            architectureFragments.filterIsInstance<EasyAdlSystem>().map { it.easyAdlComponents }.flatten()
        return freeComponents + componentsInSystems
    }
}
