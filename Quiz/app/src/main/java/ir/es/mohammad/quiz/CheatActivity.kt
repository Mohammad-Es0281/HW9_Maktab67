package ir.es.mohammad.quiz

import android.content.Intent
import android.opengl.Visibility
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import ir.es.mohammad.quiz.databinding.ActivityCheatBinding

class CheatActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCheatBinding

    private var cheated = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCheatBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val answer = intent.getBooleanExtra("Answer", false)
        with(binding) {
            btnShowAnswer.setOnClickListener {
                cheated = true
                textViewAnswer.text = answer.toString()
                textViewAnswer.visibility = View.VISIBLE
            }
        }

    }

    override fun onBackPressed() {
        val intent = Intent()
        intent.putExtra("Cheat?", cheated)
        setResult(RESULT_OK, intent)
        this.finish()
        super.onBackPressed()
    }
}