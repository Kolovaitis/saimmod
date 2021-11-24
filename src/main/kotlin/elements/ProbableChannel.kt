package elements

open class ProbableChannel(val probabilityGenerator: () -> Boolean, private var _state: Boolean = false) :
    Element<Boolean, Boolean, Boolean>, Rejectable {
    override val state: Boolean
        get() = _state

    private var _rejectedCount = 0

    override val rejectedCount: Int
        get() = _rejectedCount

    var stateCount = 0

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
        stateCount+=_state.toInt()
        if (completed) {
            _rejectedCount++
        }
        return {
            if (completed) {
                _rejectedCount--
            }
            completed
        }
    }
}