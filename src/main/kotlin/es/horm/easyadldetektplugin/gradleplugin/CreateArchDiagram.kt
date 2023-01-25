package es.horm.easyadldetektplugin.gradleplugin

import es.horm.easyadldetektplugin.interpreter.interpretArchitectureDescription
import es.horm.easyadldetektplugin.mermaid.architectureDescriptionToMermaid
import org.gradle.api.DefaultTask
import org.gradle.api.tasks.Internal
import org.gradle.api.tasks.TaskAction
import java.io.File

abstract class CreateArchDiagram : DefaultTask() {

    @Internal
    var architectureDescriptionPath: String = ""

    @TaskAction
    fun generateArchDiagram() {
        println("Generating Arch Diagram")
        project.layout.buildDirectory.get()
            .dir("reports")
            .dir("easyAdl")
            .asFile
            .mkdirs()
        project.layout.buildDirectory.get()
            .dir("reports")
            .dir("easyAdl")
            .asFile
            .resolve("archDiagram.mermaid")
            .createNewFile()

        val architectureDescriptionText = File(architectureDescriptionPath).readText()

        val architectureDescription = interpretArchitectureDescription(architectureDescriptionText)
        val mermaid = architectureDescriptionToMermaid(architectureDescription)

        project.layout.buildDirectory.get()
            .dir("reports")
            .dir("easyAdl")
            .asFile
            .resolve("archDiagram.mermaid")
            .writeText(mermaid)
    }

}
