package elements

enum class SystemState(val queue: Int?, val pi1: Boolean?, val pi2: Boolean?) {
    A(0, false, false),
    B(0, true, false),
    C(0, false, true),
    D(2, true, false),
    E(1, true, false),
    F(0, true, true),
    G(2, true, true),
    H(1, true, true),
    UNDEFINED(null, null, null);

    companion object {
        fun findState(queue: Int, pi1: Boolean, pi2: Boolean): SystemState {
            return values().find { queue == it.queue && pi1 == it.pi1 && pi2 == it.pi2 } ?: UNDEFINED
        }
    }

}