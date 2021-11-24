package elements

class ComplexSystem(val generator: Generator, val queue: Queue, val pi1: ProbableChannel, val pi2: ProbableChannel) :
    System<SystemState> {
    var processed = 0
    var generated = 0
    var requestsCount = 0

    override fun tick(): SystemState {
        val generatorSignal = generator.tick(EMPTY_LAMBDA)
        val queueSignal = queue.tick(doIfTrue(generatorSignal) { generated++ })
        val pi1Signal = pi1.tick(queueSignal)
        val pi2Signal = pi2.tick(pi1Signal)
        if (pi2Signal()) {
            processed++
        }
        val state = SystemState.findState(queue.state, pi1.state, pi2.state)
        requestsCount += state.count
        return state
    }

    companion object {
        val EMPTY_LAMBDA = {}
    }
}

fun doIfTrue(signal: () -> Boolean, toDo: () -> Unit): () -> Boolean = {
    val result = signal()
    if (result) {
        toDo()
    }
    result
}

fun doIfFalse(signal: () -> Boolean, toDo: () -> Unit): () -> Boolean = {
    val result = signal()
    if (!result) {
        toDo()
    }
    result
}