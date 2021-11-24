package elements

open class ProbableChannel(val probabilityGenerator: () -> Boolean, private var _state: Boolean = false) :
    Element<Boolean, Boolean, Boolean> {
    override val state: Boolean
        get() = _state

    override fun tick(signal: () -> Boolean): () -> Boolean {
        val completed = if (state) {
            _state = probabilityGenerator()
            !_state
        } else {
            false
        }
        if (_state == false) {
            _state = signal()
        }
        return { completed }
    }
}