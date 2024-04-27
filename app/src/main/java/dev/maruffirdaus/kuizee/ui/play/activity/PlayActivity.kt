package dev.maruffirdaus.kuizee.ui.play.activity

import android.os.Build
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.color.DynamicColors
import com.google.android.material.color.DynamicColorsOptions
import dev.maruffirdaus.kuizee.R
import dev.maruffirdaus.kuizee.data.model.Quiz
import dev.maruffirdaus.kuizee.databinding.ActivityPlayBinding
import dev.maruffirdaus.kuizee.ui.play.fragment.PlayTitleFragment

class PlayActivity : AppCompatActivity() {
    private lateinit var binding: ActivityPlayBinding
    private lateinit var quizData: Quiz

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityPlayBinding.inflate(layoutInflater)
        getData()
        setColor()
        setContentView(binding.root)
        loadFragment()
    }

    private fun getData() {
        quizData = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            intent.getParcelableExtra(EXTRA_QUIZ, Quiz::class.java) ?: Quiz()
        } else {
            @Suppress("DEPRECATION")
            intent.getParcelableExtra(EXTRA_QUIZ) ?: Quiz()
        }
    }

    private fun setColor() {
        if (quizData.color != null) {
            DynamicColors.applyToActivityIfAvailable(
                this,
                DynamicColorsOptions.Builder()
                    .setContentBasedSource(quizData.color!!)
                    .build()
            )
        }
    }

    private fun loadFragment() {
        val bundle = Bundle()
        bundle.putParcelable(PlayTitleFragment.EXTRA_QUIZ, quizData)

        val fragment = PlayTitleFragment()
        fragment.arguments = bundle

        val fragmentManager = supportFragmentManager
        fragmentManager.beginTransaction().apply {
            replace(R.id.fragmentContainer, fragment)
            commit()
        }
    }

    companion object {
        const val EXTRA_QUIZ = "extra_quiz"
    }
}