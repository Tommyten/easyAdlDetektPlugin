package es.horm.easyadldetektplugin.mermaid

import es.horm.easyadldetektplugin.model.EasyAdlComponent

interface HasMermaidFlowChartRepresentation {
    fun getMermaidFlowChartRepresentation(owningComponent: EasyAdlComponent): String
}
