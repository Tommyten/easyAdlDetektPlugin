package es.horm.easyadldetektplugin.detekt.report

import es.horm.easyadldetektplugin.detekt.ArchitectureError
import es.horm.easyadldetektplugin.detekt.config.EasyAdlArchitectureHolder
import es.horm.easyadldetektplugin.mermaid.architectureDescriptionToMermaid
import es.horm.easyadldetektplugin.model.ArchitectureDescription
import es.horm.easyadldetektplugin.model.EasyAdlComponent
import es.horm.easyadldetektplugin.detekt.processor.ArchComponentProcessor
import io.gitlab.arturbosch.detekt.api.Detektion
import io.gitlab.arturbosch.detekt.api.Entity
import io.gitlab.arturbosch.detekt.api.OutputReport
import io.gitlab.arturbosch.detekt.api.SourceLocation
import io.gitlab.arturbosch.detekt.api.TextLocation
import kotlinx.html.FlowContent
import kotlinx.html.code
import kotlinx.html.div
import kotlinx.html.h3
import kotlinx.html.p
import kotlinx.html.pre
import kotlinx.html.span
import kotlinx.html.stream.createHTML
import kotlin.math.max
import kotlin.math.min

class ArchReport : OutputReport() {

    private var architectureDescription: ArchitectureDescription =
        EasyAdlArchitectureHolder.architectureDescription ?: throw IllegalStateException()
    override val ending: String = ".arch.html"

    override fun render(detektion: Detektion): String {
        val mermaid = architectureDescriptionToMermaid(architectureDescription)

        val identifiedComponents = detektion.getData(ArchComponentProcessor.identifiedComponentsKey) ?: mapOf()

        val architectureFindings: List<ArchitectureError> =
            detektion.findings["EasyAdlRuleSet"]?.filterIsInstance<ArchitectureError>() ?: listOf()

        return buildString {
            append(
                """
<!DOCTYPE html>
<html>
<head>
<script type="module">
import mermaid from 'https://cdn.jsdelivr.net/npm/mermaid@9/dist/mermaid.esm.min.mjs';
mermaid.initialize({startOnLoad: true});
</script>
<style>
    @import url('https://fonts.googleapis.com/css2?family=JetBrains+Mono:wght@400;500&display=swap');
</style>
<style>
code {
    font-family: JetBrains Mono, monospace;
    font-weight: 400;
}

.lineno {
    margin-right: 24px;
    color: #9771c3;
    background-color: rgba(230, 211, 243, 0.51);
}

pre {
    font-family: JetBrains Mono, monospace;
    font-weight: 500;
    border: 1px solid #e0e0e0;
    overflow: auto;
}
</style>
</head>
<body>
<div class="mermaid">
$mermaid
</div>
<div>
${renderTest(identifiedComponents)}
</div>
<div>
${renderArchitectureFindings(architectureFindings)}
</div>
</body>
</html>
            """.trimIndent()
            )
        }
    }

    private fun renderTest(identifiedComponents: Map<EasyAdlComponent, List<Entity>>) = createHTML().div {
        for (identified in identifiedComponents) {
            componentHeading(identified.key.name)
            for (occurrence in identified.value) {
                renderComponents(occurrence)
            }
        }
    }

    private fun renderArchitectureFindings(architectureFindings: List<ArchitectureError>) =
        createHTML().div {
            architectureFindings.forEach {
                renderArchitectureFinding(it)
            }
        }

}

private fun FlowContent.renderArchitectureFinding(architectureError: ArchitectureError) {
    h3 { text(architectureError.entity.signature) }
    p { text(architectureError.message) }
}

private fun FlowContent.renderComponents(entity: Entity) {
    val psiFile = entity.ktElement?.containingFile
    entity.location.source
    if (psiFile != null) {
        val lineSequence = psiFile.text.splitToSequence('\n')
        snippetCode(lineSequence, entity.location.source, entity.location.text.length())
    }
}

private fun TextLocation.length(): Int = end - start
private const val EXTRA_LINES_IN_SNIPPET = 1

private fun FlowContent.componentHeading(componentName: String) {
    h3 { text(componentName) }
}

private fun FlowContent.snippetCode(
    lines: Sequence<String>,
    location: SourceLocation,
    length: Int
) {
    try {
        pre {
            code {
                val dropLineCount = max(location.line - 1 - EXTRA_LINES_IN_SNIPPET, 0)
                val takeLineCount = EXTRA_LINES_IN_SNIPPET + 1 + min(location.line - 1, EXTRA_LINES_IN_SNIPPET)
                var currentLineNumber = dropLineCount + 1
                var errorLength = length
                lines
                    .drop(dropLineCount)
                    .take(takeLineCount)
                    .forEach { line ->
                        span("lineno") { text("%1$4s ".format(currentLineNumber)) }
                        if (currentLineNumber >= location.line && errorLength > 0) {
                            val column = if (currentLineNumber == location.line) location.column - 1 else 0
                            errorLength -= writeErrorLine(line, column, errorLength) + 1 // we need to consume the \n
                        } else {
                            text(line)
                        }
                        text("\n")
                        currentLineNumber++
                    }
            }
        }
    } catch (@Suppress("TooGenericExceptionCaught") ex: Throwable) {

    }
}

private fun FlowContent.writeErrorLine(line: String, errorStarts: Int, length: Int): Int {
    val errorEnds = min(errorStarts + length, line.length)
    text(line.substring(startIndex = 0, endIndex = errorStarts))
    span("error") {
        text(
            line.substring(
                startIndex = errorStarts,
                endIndex = errorEnds
            )
        )
    }
    text(line.substring(startIndex = errorEnds))
    return errorEnds - errorStarts
}

