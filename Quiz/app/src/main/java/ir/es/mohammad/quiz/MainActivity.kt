package ir.es.mohammad.quiz

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import ir.es.mohammad.quiz.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private val questions =
        Array(10) { "Is this a homework from ${if (it % 2 == 0) "maktab" else "university"}?" }
    private val answers = Array(10) { it % 2 == 0 }
    private var userAnswers = Array(10) { AnswerStatus.NotAnswered }
    private var questionNumber = 0

    private fun showToast(message: String) = Toast.makeText(this, message, Toast.LENGTH_LONG).show()

    private lateinit var intentActivityResultLauncher: ActivityResultLauncher<Intent>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        createIntentActivityResultLauncher()
        if (savedInstanceState?.containsKey("questionNumber") == true)
            questionNumber = savedInstanceState.getInt("questionNumber")
        if (savedInstanceState?.containsKey("AnswerStatus") == true)
            userAnswers =
                savedInstanceState.getIntArray("AnswerStatus")?.map { AnswerStatus.values()[it] }
                    ?.toTypedArray() ?: Array(10) { AnswerStatus.NotAnswered }

        with(binding) {
            textViewQuestion.text = questions[questionNumber]
            if (questionNumber == 0) btnPrev.isEnabled = false
            else if (questionNumber == 9) btnNext.isEnabled = false
            if (userAnswers[questionNumber] != AnswerStatus.NotAnswered) answerButtonsSetIsEnable(false)

            btnTrue.setOnClickListener { answerButtonClickListener(answers[questionNumber]) }

            btnFalse.setOnClickListener { answerButtonClickListener(!answers[questionNumber]) }

            btnCheat.setOnClickListener {
                val intent = Intent(this@MainActivity, CheatActivity::class.java)
                intent.putExtra("Answer", answers[questionNumber])
                intentActivityResultLauncher.launch(intent)
                answerButtonsSetIsEnable(false)
            }

            btnPrev.setOnClickListener {
                textViewQuestion.text = questions[--questionNumber]
                btnPrev.isEnabled = questionNumber != 0
                btnNext.isEnabled = true
                answerButtonsSetIsEnable(userAnswers[questionNumber] == AnswerStatus.NotAnswered)
                if (userAnswers[questionNumber] == AnswerStatus.Cheat)
                    showToast("Cheat ☹️ Answer = ${answers[questionNumber]}")
            }

            btnNext.setOnClickListener {
                textViewQuestion.text = questions[++questionNumber]
                btnNext.isEnabled = questionNumber != 9
                btnPrev.isEnabled = true
                answerButtonsSetIsEnable(userAnswers[questionNumber] == AnswerStatus.NotAnswered)
                if (userAnswers[questionNumber] == AnswerStatus.Cheat)
                    showToast("Cheat ☹️ Answer is ${answers[questionNumber]}")
            }
        }
    }

    private fun answerButtonClickListener(isCorrect: Boolean) {
        if (isCorrect) {
            showToast("Correct!")
            userAnswers[questionNumber] = AnswerStatus.Correct
        } else {
            showToast("Incorrect!")
            userAnswers[questionNumber] = AnswerStatus.Incorrect
        }
        answerButtonsSetIsEnable(false)
    }

    private fun answerButtonsSetIsEnable(isEnabled: Boolean) {
        with(binding) {
            btnTrue.isEnabled = isEnabled
            btnFalse.isEnabled = isEnabled
            btnCheat.isEnabled = isEnabled
        }
    }

    private fun createIntentActivityResultLauncher() {
        intentActivityResultLauncher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
                if (it.data?.getBooleanExtra("Cheat?", false) == true) {
                    showToast("Cheating is bad ☹️ Answer is ${answers[questionNumber]}")
                    userAnswers[questionNumber] = AnswerStatus.Cheat
                }
            }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putInt("questionNumber", questionNumber)
        outState.putIntArray("AnswerStatus", userAnswers.map { it.ordinal }.toIntArray())
    }
}

private enum class AnswerStatus() {
    NotAnswered, Correct, Incorrect, Cheat
}