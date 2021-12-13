import elements.*
import elements.Queue
import java.util.*

const val START_GENERATOR = 0.5
const val PI1_GENERATOR = 0.9
const val PI2_GENERATOR = 0.0
const val COUNT = 10000000
const val COUNT_MATH = COUNT.toDouble()
val random = Random()

fun getBooleanWithProbability(probability: Double): Boolean {
    return random.nextDouble() < probability
}

val startGenerator = {
    getBooleanWithProbability(START_GENERATOR)
}
val pi1Generator = {
    getBooleanWithProbability(PI1_GENERATOR)
}
val pi2Generator = {
    getBooleanWithProbability(PI2_GENERATOR)
}

fun main(args: Array<String>) {
    val generator = Generator(startGenerator)
    val queue = Queue(0)
    val pi1 = ProbableChannel(pi1Generator, false)
    val pi2 = ProbableChannel(pi2Generator, false)
    val system = ComplexSystem(generator, queue, pi1, pi2)
    val states = mutableMapOf<SystemState, Int>()
    for (i in 1..COUNT) {
        val state = system.tick()
        states[state] = (states[state] ?: 0) + 1
    }
    SystemState.values().forEach { state ->
        if (states.containsKey(state)) {
            val part = states[state]?.div(COUNT_MATH)
            println("${state.name} $part")
        }
    }
    val A = system.processed / COUNT_MATH
    val Q = system.processed / system.generated.toDouble()
    val rejectQ = (pi1.rejectedCount + queue.rejectedCount) / system.generated.toDouble()
    println("Абсолютная пропускная способность $A")
    println("Относительная пропускная способность $Q")
    println("Веротность отказа $rejectQ")
    println("Средняя длина очереди ${queue.stateSum / COUNT_MATH}")
    println("Среднее число заявок в системе ${system.requestsCount / COUNT_MATH}")
    val timeInQueue = (queue.stateSum) / (system.generated - queue.rejectedCount).toDouble()
    val timeInPi1 = (pi1.stateCount) / (system.generated - queue.rejectedCount - pi1.rejectedCount).toDouble()
    val timeInPi2 = (pi2.stateCount) / (system.processed).toDouble()
    println("Среднее время пребывания заявки в очереди $timeInQueue")
    println("Среднее время пребывания заявки в системе ${timeInQueue + timeInPi1 + timeInPi2}")
    println("Коэффициент загрузки канала pi1 ${pi1.stateCount / COUNT_MATH}")
    println("Коэффициент загрузки канала pi2 ${pi2.stateCount / COUNT_MATH}")

}