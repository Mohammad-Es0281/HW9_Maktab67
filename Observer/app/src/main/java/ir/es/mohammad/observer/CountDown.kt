package ir.es.mohammad.observer

import android.os.CountDownTimer
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleOwner
import java.util.*

class CountDown: Publisher {
    val numbers = LinkedList<Int>( )

    private val timer = object: CountDownTimer(300000, 3000) {
        override fun onTick(millisUntilFinished: Long) {
            notify("Number: ${numbers.removeFirst()}")
        }

        override fun onFinish() {
            notify("Finished")
        }
    }

    private fun start(){
        if (numbers.isEmpty())
            numbers.addAll(1 .. 100)
        timer.start()
    }

    private val subscribers = mutableSetOf<Subscriber>()

    override fun subscribe(s: Subscriber){
        if(subscribers.isEmpty())
            start()
        subscribers.add(s)
    }

    override fun unsubscribe(s: Subscriber){
        subscribers.remove(s)
        if(subscribers.isEmpty())
            timer.cancel()
    }

    override fun notify(message: String){
        subscribers.forEach { it.update(message) }
    }

    fun byLifecycle(lifecycleOwner: LifecycleOwner) {
        val observer = LifecycleEventObserver { _, event ->
            if (event == Lifecycle.Event.ON_RESUME) {
                if (subscribers.isNotEmpty())
                    timer.start()
            } else if (event == Lifecycle.Event.ON_PAUSE) {
                timer.cancel()
            }
        }

        lifecycleOwner.lifecycle.addObserver(observer)
    }
}