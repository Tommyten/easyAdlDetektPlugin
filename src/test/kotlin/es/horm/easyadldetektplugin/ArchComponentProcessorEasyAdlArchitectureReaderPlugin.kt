package es.horm.easyadldetektplugin

import es.horm.easyadldetektplugin.detekt.processor.ArchComponentProcessor
import io.github.detekt.test.utils.compileContentForTest
import io.gitlab.arturbosch.detekt.api.Detektion
import io.gitlab.arturbosch.detekt.api.Finding
import io.gitlab.arturbosch.detekt.api.Notification
import io.gitlab.arturbosch.detekt.api.ProjectMetric
import io.gitlab.arturbosch.detekt.api.RuleSetId
import io.gitlab.arturbosch.detekt.rules.KotlinCoreEnvironmentTest
import io.gitlab.arturbosch.detekt.test.getContextForPaths
import org.jetbrains.kotlin.cli.jvm.compiler.KotlinCoreEnvironment
import org.jetbrains.kotlin.com.intellij.openapi.util.Key
import org.jetbrains.kotlin.com.intellij.util.keyFMap.KeyFMap
import org.junit.jupiter.api.Test

@KotlinCoreEnvironmentTest
class ArchComponentProcessorEasyAdlArchitectureReaderPlugin(private val env: KotlinCoreEnvironment) {

    @Test
    fun `should expect two loops`() {
        val architectureDescription = """
            component ViewModel:
              has suffix "ViewModel"
              must reference component Repository
              
            component Repository:
              has suffix "Repository"

            component UI:
              has Suffix "Screen"
              is annotated with "Composable"
              must reference component ViewModel
              may not reference component Repository
        """.trimIndent()

        val code = """
class TestRepository {
    
}

class TestViewModel {
    val repository = TestRepository()
}

@Composable
fun TestScreen() {
    val viewModel = TestViewModel()
    //val repository = TestRepository() uncommenting breaks test
}

annotation class Composable
        """

        val ktFile = compileContentForTest(code)
        val detektion = DetektResult(mapOf())

        val bindingContext = env.getContextForPaths(listOf(ktFile))
        val testSubject = ArchComponentProcessor().apply { architectureDescriptionText = architectureDescription }
        testSubject.onProcess(ktFile, bindingContext)
        testSubject.onFinish(listOf(ktFile), detektion, bindingContext)

        assert(ktFile.getUserData(ArchComponentProcessor.identifiedComponentsKey)?.size == 3)
    }
}

data class DetektResult(override val findings: Map<RuleSetId, List<Finding>>) : Detektion {

    private val _notifications = ArrayList<Notification>()
    override val notifications: Collection<Notification> = _notifications

    private val _metrics = ArrayList<ProjectMetric>()
    override val metrics: Collection<ProjectMetric> = _metrics

    private var userData = KeyFMap.EMPTY_MAP

    override fun add(projectMetric: ProjectMetric) {
        _metrics.add(projectMetric)
    }

    override fun add(notification: Notification) {
        _notifications.add(notification)
    }

    override fun <V> getData(key: Key<V>): V? = userData[key]

    override fun <V> addData(key: Key<V>, value: V) {
        userData = userData.plus(key, requireNotNull(value))
    }
}
