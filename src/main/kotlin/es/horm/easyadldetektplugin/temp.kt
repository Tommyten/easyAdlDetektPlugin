class SampleViewModel {

    private val repository = SampleRepository()

    fun doSampleBusinessLogic(): Int {
        return repository.getData()
    }
}


class SampleView {

    private val sampleViewModel = SampleViewModel()

    fun render() {
        println(sampleViewModel.doSampleBusinessLogic())
    }
}


const val UPPER_BOUND = 10

class SampleRepository {
    val testView = SampleView()

    init {
        testView.render()
    }

    fun getData(): Int = (0..UPPER_BOUND).random()
}
