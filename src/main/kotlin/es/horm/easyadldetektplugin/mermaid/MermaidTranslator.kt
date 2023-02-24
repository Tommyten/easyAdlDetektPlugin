package es.horm.easyadldetektplugin.mermaid

import es.horm.easyadldetektplugin.interpreter.interpretArchitectureDescription
import es.horm.easyadldetektplugin.model.ArchitectureDescription
import es.horm.easyadldetektplugin.model.EasyAdlSystem

fun architectureDescriptionToMermaid(architectureDescription: ArchitectureDescription): String {
    val systems = architectureDescription.architectureFragments.filterIsInstance<EasyAdlSystem>()
    val allComponents = architectureDescription.getAllComponents()

    val stringBuilder = StringBuilder()
    stringBuilder.appendLine("flowchart")

    for(component in allComponents) {
        component.easyAdlOperations.filterIsInstance<HasMermaidFlowChartRepresentation>().forEach {
            it.getMermaidFlowChartRepresentation(component)?.let { mermaidRepresentation ->
                stringBuilder.appendLine(mermaidRepresentation)
            }
        }
    }

    for (system in systems) {
        stringBuilder.appendLine("subgraph ${system.name}")
        for (component in system.easyAdlComponents) {
            stringBuilder.appendLine(component.name)
        }
        stringBuilder.appendLine("end")
    }
    return stringBuilder.toString()
}

fun main() {
    val architectureDescriptionText = """
component DataSource:
    has suffix "DataSource"
    
system MVVM:
    component Model:
      has suffix "Model"
      must reference component DataSource
      
    component ViewModel:
      has suffix "ViewModel"
      must reference component Model
      must reference component View

    component View:
      has Suffix "View"
      must reference component ViewModel
      may not reference component DataSource (hidden)
    """.trimIndent()
    val architectureDescription = interpretArchitectureDescription(architectureDescriptionText)
    println(architectureDescriptionToMermaid(architectureDescription))
}
