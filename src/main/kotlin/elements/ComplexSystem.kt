package elements

class ComplexSystem(val generator: Generator, val queue: Queue, val pi1: ProbableChannel, val pi2: ProbableChannel) :
    System<SystemState> {
    override fun tick(): SystemState {
        val generatorSignal = generator.tick(EMPTY_LAMBDA)
        val queueSignal = queue.tick(generatorSignal)
        val pi1Signal = pi1.tick(queueSignal)
        val pi2Signal = pi2.tick(pi1Signal)
        return SystemState.findState(queue.state, pi1.state, pi2.state)
    }
    companion object{
        val EMPTY_LAMBDA = {}
    }
}