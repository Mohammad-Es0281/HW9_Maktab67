package ir.es.mohammad.observer

interface Subscriber {
    val name: String
    var publisher: Publisher?
    fun update(message: String)
}
