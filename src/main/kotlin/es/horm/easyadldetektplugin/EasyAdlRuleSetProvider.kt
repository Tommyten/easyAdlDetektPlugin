package es.horm.easyadldetektplugin

import io.gitlab.arturbosch.detekt.api.Config
import io.gitlab.arturbosch.detekt.api.RuleSet
import io.gitlab.arturbosch.detekt.api.RuleSetProvider

class EasyAdlRuleSetProvider : RuleSetProvider {
    override val ruleSetId: String = RULE_SET_ID

    override fun instance(config: Config): RuleSet {
        return RuleSet(ruleSetId, listOf(EasyAdlComplianceRule(config)))
    }

    companion object {
        const val RULE_SET_ID = "EasyAdlRuleSet"
    }
}
