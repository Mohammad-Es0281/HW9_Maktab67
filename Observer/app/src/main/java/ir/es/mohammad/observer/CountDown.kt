class Publisher1: Publisher {
    private val subscribers = mutableListOf<Subscriber>()

    override fun subscribe(s: Subscriber){
        subscribers.add(s)
    }

    override fun unsubscribe(s: Subscriber){
        subscribers.remove(s)
    }

    override fun notify(message: String){
        subscribers.forEach { it.update(message) }
    }
}