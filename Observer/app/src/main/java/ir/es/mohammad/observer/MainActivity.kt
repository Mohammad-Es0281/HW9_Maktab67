package ir.es.mohammad.observer

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import ir.es.mohammad.observer.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private lateinit var countDown: CountDown

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        countDown = CountDown()
        countDown.byLifecycle(this)

        with(binding) {
            val subscriber1 = ASubscriber("subscriber1", textView1)
            val subscriber2 = ASubscriber("subscriber2", textView2)
            val subscriber3 = ASubscriber("subscriber3", textView3)

            btnSubscribe1.setOnClickListener { setBtnOnClick(btnSubscribe1, subscriber1) }
            btnSubscribe2.setOnClickListener { setBtnOnClick(btnSubscribe2, subscriber2) }
            btnSubscribe3.setOnClickListener { setBtnOnClick(btnSubscribe3, subscriber3) }
        }
    }

    private inner class ASubscriber(override val name: String, val textView: TextView): Subscriber{
        override var publisher: Publisher? = null

        override fun update(message: String) {
            val string = "$name - $message"
            Log.d("Subscriber", string)
            textView.text = string
        }
    }

    private fun setBtnOnClick(button: Button, subscriber: Subscriber){
        if (subscriber.publisher != null){
            subscriber.publisher = null
            countDown.unsubscribe(subscriber)
            Log.d("Subscriber","${subscriber.name} unsubscribed")
            button.text = "Subscribe"
        }
        else{
            subscriber.publisher = countDown
            countDown.subscribe(subscriber)
            Log.d("Subscriber","${subscriber.name} subscribed")
            button.text = "Unsubscribe"
        }
    }
}