import elements.*
import elements.Queue
import java.util.*

const val START_GENERATOR = 0.5
const val PI1_GENERATOR = 0.6
const val PI2_GENERATOR = 0.6

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
    for (i in 1..1000000) {
        val state = system.tick()
        states[state] = (states[state] ?: 0) + 1
    }
    states.forEach { (state, probability) ->
        println("${state.name} ${probability / 1000000.0}")
    }
}