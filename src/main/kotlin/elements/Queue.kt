package elements

class Queue(private var _state: Int = 0) : Element<Boolean, Int, Boolean>, Rejectable {
    private var _rejected = 0
    override val rejectedCount: Int
        get() = _rejected
    override val state: Int
        get() = _state

    var stateSum = 0

    override fun tick(signal: () -> Boolean): () -> Boolean {
        val signalValue = signal()
        val pastValue = state
        if (signalValue && state < 2) {
            _state++
        }
        stateSum += state
        if (pastValue >= 2 && signalValue) {
            _rejected++
        }
        return {
            when {
                signalValue -> {
                    if (pastValue >= 2) {
                        _rejected--
                    }else{
                        stateSum--
                    }
                    _state = pastValue
                    true
                }
                state > 0 -> {
                    stateSum--
                    _state--
                    true
                }
                else -> false
            }
        }
    }
}