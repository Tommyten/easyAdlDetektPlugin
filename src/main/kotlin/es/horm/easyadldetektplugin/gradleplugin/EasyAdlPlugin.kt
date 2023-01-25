package es.horm.easyadldetektplugin.gradleplugin

import org.gradle.api.Plugin
import org.gradle.api.Project

class EasyAdlPlugin : Plugin<Project> {
    override fun apply(project: Project) {
        println("EasyAdlPlugin Successfully applied")
        val extension = project.extensions.create("easyAdl", EasyAdlExtension::class.java)
        project.registerCreateArchDiagramTask(extension)
    }

    private fun Project.registerCreateArchDiagramTask(extension: EasyAdlExtension) {
        tasks.register("createArchDiagram", CreateArchDiagram::class.java) {
            it.architectureDescriptionPath = extension.archDescriptionPath
        }
    }
}
