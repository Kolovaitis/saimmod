package elements

interface Element<Signal, State, Result> {
    val state: State
    fun tick(signal: () -> Signal): ()->Result
}