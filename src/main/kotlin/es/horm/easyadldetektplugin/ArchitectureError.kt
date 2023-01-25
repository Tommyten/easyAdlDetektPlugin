package es.horm.easyadldetektplugin

import io.gitlab.arturbosch.detekt.api.CodeSmell
import io.gitlab.arturbosch.detekt.api.Entity
import io.gitlab.arturbosch.detekt.api.Issue

class ArchitectureError(
    issue: Issue, entity: Entity, message: String
) : CodeSmell(issue, entity, message)
