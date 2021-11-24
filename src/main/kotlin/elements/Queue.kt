package elements

class Queue(private var _state: Int = 0) : Element<Boolean, Int, Boolean> {
    override val state: Int
        get() = _state

    override fun tick(signal: () -> Boolean): () -> Boolean {
        val signalValue = signal()
        val pastValue = state
        if (signalValue && state < 2) {
            _state++
        }
        return {
            when {
                signalValue -> {
                    _state = pastValue
                    true
                }
                state > 0 -> {
                    _state--
                    true
                }
                else -> false
            }
        }
    }
}