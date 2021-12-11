package com.nghanyi.quizapp

import android.graphics.Color
import android.graphics.Typeface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.nghanyi.quizapp.databinding.ActivityMainBinding
import com.nghanyi.quizapp.databinding.ActivityQuizQuestionBinding
import org.w3c.dom.Text

class QuizQuestionActivity : AppCompatActivity() {

    private var mCurrentPosition: Int = 1
    private var mQuestionList: ArrayList<Question>? = null
    private var mSelectedOptionPosition: Int = 0

    private lateinit var binding: ActivityQuizQuestionBinding // So no need to use findViewById

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityQuizQuestionBinding.inflate(LayoutInflater.from(this))
        setContentView(binding.root)

        mQuestionList = Constants.getQuestions()
        mCurrentPosition = 1

        setQuestion()
        showProgress()
    }

    /**
     * Set current question to be shown
     */
    private fun setQuestion() {
        val question = mQuestionList!![mCurrentPosition-1]

        defaultOptionsView()

        binding.ivImage.setImageResource(question.image)
        binding.tvQuestion.text = question.question
        binding.tvOptionOne.text = question.optionOne
        binding.tvOptionTwo.text = question.optionTwo
        binding.tvOptionThree.text = question.optionThree
        binding.tvOptionFour.text = question.optionFour
    }

    /**
     * Set current progress of user
     */
    private fun showProgress() {
        binding.progressBar.progress = mCurrentPosition
        binding.tvProgress.text = "$mCurrentPosition/${binding.progressBar.max}"
    }

    private fun defaultOptionsView() {
        val options = ArrayList<TextView>()
        options.add(0, binding.tvOptionOne)
        options.add(1, binding.tvOptionTwo)
        options.add(2, binding.tvOptionThree)
        options.add(3, binding.tvOptionFour)

        for (option in options) {
            option.typeface = Typeface.DEFAULT
            option.setTextColor(ContextCompat.getColor(this, R.color.gray))
            option.background = ContextCompat.getDrawable(this, R.drawable.default_option_border_bg)
        }
    }
}