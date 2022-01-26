package observer

interface Publisher {
    fun subscribe(s: Subscriber)
    fun unsubscribe(s: Subscriber)
    fun notify(message: String)
}