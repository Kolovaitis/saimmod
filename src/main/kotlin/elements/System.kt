package elements

interface System<State> {
    fun tick():State
}