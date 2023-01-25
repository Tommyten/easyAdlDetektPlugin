package es.horm.easyadldetektplugin.gradleplugin

import org.gradle.api.plugins.quality.CodeQualityExtension

open class EasyAdlExtension : CodeQualityExtension() {
    var archDescriptionPath: String = ""
}
