package observer

interface Subscriber {
    val publisher: Publisher
    fun update(message: String)
}
