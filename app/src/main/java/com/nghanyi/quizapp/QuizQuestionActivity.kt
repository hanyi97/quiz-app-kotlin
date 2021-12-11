package com.nghanyi.quizapp

import android.content.Intent
import android.graphics.Typeface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.nghanyi.quizapp.databinding.ActivityQuizQuestionBinding

class QuizQuestionActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var binding: ActivityQuizQuestionBinding // So no need to use findViewById

    private var mCurrentPosition: Int = 1
    private var mQuestionList: ArrayList<Question>? = null
    private var mSelectedOptionPosition: Int = 0
    private var mCorrectAnswers: Int = 0
    private var mUsername: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityQuizQuestionBinding.inflate(LayoutInflater.from(this))
        setContentView(binding.root)

        mUsername = intent.getStringExtra(Constants.USER_NAME)

        mQuestionList = Constants.getQuestions()
        mCurrentPosition = 1

        setQuestion()
        setProgress()

        binding.tvOptionOne.setOnClickListener(this)
        binding.tvOptionTwo.setOnClickListener(this)
        binding.tvOptionThree.setOnClickListener(this)
        binding.tvOptionFour.setOnClickListener(this)
        binding.btnSubmit.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when(v?.id) {
            R.id.tv_option_one -> selectedOptionView(binding.tvOptionOne, 1)
            R.id.tv_option_two -> selectedOptionView(binding.tvOptionTwo, 2)
            R.id.tv_option_three -> selectedOptionView(binding.tvOptionThree, 3)
            R.id.tv_option_four -> selectedOptionView(binding.tvOptionFour, 4)
            R.id.btn_submit -> checkAnswer()
        }
    }

    /**
     * Set current question to be shown
     */
    private fun setQuestion() {
        val question = mQuestionList!![mCurrentPosition-1]

        defaultOptionsView()

        if (mCurrentPosition == mQuestionList!!.size) {
            binding.btnSubmit.text = "FINISH"
        } else {
            binding.btnSubmit.text = "SUBMIT"
        }

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
    private fun setProgress() {
        binding.progressBar.progress = mCurrentPosition
        binding.tvProgress.text = "$mCurrentPosition/${binding.progressBar.max}"
    }

    /**
     * Set all options to default
     */
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

    /**
     * Set selected option
     */
    private fun selectedOptionView(tv: TextView, selectedOptionNum: Int) {
        defaultOptionsView()
        mSelectedOptionPosition = selectedOptionNum
        tv.setTypeface(tv.typeface, Typeface.BOLD)
        tv.setTextColor(ContextCompat.getColor(this, R.color.gray))
        tv.background = ContextCompat.getDrawable(this, R.drawable.selected_option_border_bg)
    }

    /**
     * Display answer border accordingly
     */
    private fun answerView(answer: Int, drawableView: Int) {
        when(answer) {
            1 -> binding.tvOptionOne.background = ContextCompat.getDrawable(this, drawableView)
            2 -> binding.tvOptionTwo.background = ContextCompat.getDrawable(this, drawableView)
            3 -> binding.tvOptionThree.background = ContextCompat.getDrawable(this, drawableView)
            4 -> binding.tvOptionFour.background = ContextCompat.getDrawable(this, drawableView)

        }
    }

    /**
     * Check if answer is correct.
     */
    private fun checkAnswer() {
        if (mSelectedOptionPosition == 0) {
            mCurrentPosition++

            when{
                mCurrentPosition <= mQuestionList!!.size -> {
                    setQuestion()
                    setProgress()
                } else -> showResultsActivity()
            }
        } else {
            val question = mQuestionList!![mCurrentPosition - 1]
            // Wrong answer
            if(question.correctAnswer != mSelectedOptionPosition) {
                answerView(mSelectedOptionPosition, R.drawable.wrong_option_border_bg)
            } else {
                mCorrectAnswers++
            }
            // Show correct answer
            answerView(question.correctAnswer, R.drawable.correct_option_border_bg)

            if (mCurrentPosition == mQuestionList!!.size) {
                binding.btnSubmit.text = "FINISH"
            } else {
                binding.btnSubmit.text = "GO TO NEXT QUESTION"
            }
            mSelectedOptionPosition = 0
        }
    }

    /**
     * Navigate to result screen
     */
    private fun showResultsActivity() {
        val intent = Intent(this, ResultActivity::class.java)
        intent.putExtra(Constants.USER_NAME, mUsername)
        intent.putExtra(Constants.CORRECT_ANSWERS, mCorrectAnswers)
        intent.putExtra(Constants.TOTAL_QUESTIONS, mQuestionList!!.size)
        startActivity(intent)
        finish()
    }
}