package ir.es.mohammad.observer

import ir.es.mohammad.observer.Subscriber

interface Publisher {
    fun subscribe(s: Subscriber)
    fun unsubscribe(s: Subscriber)
    fun notify(message: String)
}