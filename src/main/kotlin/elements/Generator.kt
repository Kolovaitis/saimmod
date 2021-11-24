package elements

class Generator(val probabilityGenerator: () -> Boolean) : Element<Unit, Unit, Boolean> {
    override val state: Unit
        get() = Unit

    override fun tick(signal: () -> Unit): () -> Boolean {
        return { !probabilityGenerator() }
    }
}