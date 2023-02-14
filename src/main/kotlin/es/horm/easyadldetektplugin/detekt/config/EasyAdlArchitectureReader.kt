package es.horm.easyadldetektplugin.detekt.config

import es.horm.easyadldetektplugin.ARCHITECTURE_DESCRIPTION_PATH_CONFIG_KEY
import es.horm.easyadldetektplugin.detekt.rule.EasyAdlComplianceRule
import es.horm.easyadldetektplugin.detekt.rule.EasyAdlRuleSetProvider
import es.horm.easyadldetektplugin.interpreter.interpretArchitectureDescription
import es.horm.easyadldetektplugin.model.ArchitectureDescription
import io.gitlab.arturbosch.detekt.api.Config
import io.gitlab.arturbosch.detekt.api.ConfigValidator
import io.gitlab.arturbosch.detekt.api.Notification
import io.gitlab.arturbosch.detekt.api.internal.SimpleNotification
import java.io.File
import java.nio.file.Paths

class EasyAdlArchitectureReader : ConfigValidator {

    lateinit var architectureDescription: ArchitectureDescription

    override fun validate(config: Config): Collection<Notification> {
        val architectureDescriptionPath = config
            .subConfig(EasyAdlRuleSetProvider.RULE_SET_ID)
            .subConfig(EasyAdlComplianceRule::class.java.simpleName).
            valueOrNull<String>(ARCHITECTURE_DESCRIPTION_PATH_CONFIG_KEY)
        if(architectureDescriptionPath.isNullOrBlank()) {
            return listOf(SimpleNotification("The architecture description path may not be blank"))
        }
        val architectureDescriptionText = File(Paths.get(architectureDescriptionPath).toAbsolutePath().toString()).readText()
        if(architectureDescriptionPath.isBlank()) {
            return listOf(SimpleNotification("The architecture description may not be blank"))
        }
        architectureDescription = interpretArchitectureDescription(architectureDescriptionText)
        EasyAdlArchitectureHolder.architectureDescription = architectureDescription
        return emptyList()
    }
}

object EasyAdlArchitectureHolder {
    var architectureDescription: ArchitectureDescription? = null
}
