package com.nghanyi.quizapp

import android.graphics.Color
import android.graphics.Typeface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.nghanyi.quizapp.databinding.ActivityMainBinding
import com.nghanyi.quizapp.databinding.ActivityQuizQuestionBinding
import org.w3c.dom.Text

class QuizQuestionActivity : AppCompatActivity(), View.OnClickListener {

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

        binding.tvOptionOne.setOnClickListener(this)
        binding.tvOptionTwo.setOnClickListener(this)
        binding.tvOptionThree.setOnClickListener(this)
        binding.tvOptionFour.setOnClickListener(this)

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
            option.setTextColor(ContextCompat.getColor(this, R.color.lightGray))
            option.background = ContextCompat.getDrawable(this, R.drawable.default_option_border_bg)
        }
    }

    override fun onClick(v: View?) {
        when(v?.id) {
            R.id.tv_option_one -> selectedOptionView(binding.tvOptionOne, 1)
            R.id.tv_option_two -> selectedOptionView(binding.tvOptionTwo, 2)
            R.id.tv_option_three -> selectedOptionView(binding.tvOptionThree, 3)
            R.id.tv_option_four -> selectedOptionView(binding.tvOptionFour, 4)
        }
    }

    private fun selectedOptionView(tv: TextView, selectedOptionNum: Int) {
        defaultOptionsView()
        mSelectedOptionPosition = selectedOptionNum
        tv.setTypeface(tv.typeface, Typeface.BOLD)
        tv.setTextColor(ContextCompat.getColor(this, R.color.gray))
        tv.background = ContextCompat.getDrawable(this, R.drawable.selected_option_border_bg)
    }
}