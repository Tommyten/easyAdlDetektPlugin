package es.horm.easyadldetektplugin.detekt

import io.gitlab.arturbosch.detekt.api.CodeSmell
import io.gitlab.arturbosch.detekt.api.Entity
import io.gitlab.arturbosch.detekt.api.Issue

class ArchitectureError(
    val componentName: String,
    issue: Issue,
    entity: Entity,
    message: String
) : CodeSmell(issue, entity, message)
