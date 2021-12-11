package com.nghanyi.quizapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import com.nghanyi.quizapp.databinding.ActivityResultBinding

class ResultActivity : AppCompatActivity() {

    private lateinit var binding: ActivityResultBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityResultBinding.inflate(LayoutInflater.from(this))
        setContentView(binding.root)

        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_FULLSCREEN
        actionBar?.hide()

        val username = intent.getStringExtra(Constants.USER_NAME)
        binding.tvName.text = username

        val correctAnswers = intent.getIntExtra(Constants.CORRECT_ANSWERS, 0)
        val totalQuestions = intent.getIntExtra(Constants.TOTAL_QUESTIONS, 0)
        binding.tvScore.text = "Your Score is $correctAnswers out of $totalQuestions"

        binding.btnFinish.setOnClickListener{
            startActivity(Intent(this, MainActivity::class.java))
        }
    }
}