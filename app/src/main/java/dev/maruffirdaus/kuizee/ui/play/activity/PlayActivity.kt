package dev.maruffirdaus.kuizee.ui.play.activity

import android.os.Build
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.color.DynamicColors
import com.google.android.material.color.DynamicColorsOptions
import dev.maruffirdaus.kuizee.R
import dev.maruffirdaus.kuizee.data.model.Topic
import dev.maruffirdaus.kuizee.databinding.ActivityPlayBinding
import dev.maruffirdaus.kuizee.ui.play.fragment.PlayTitleFragment

class PlayActivity : AppCompatActivity() {
    private lateinit var binding: ActivityPlayBinding
    private lateinit var topicData: Topic

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
        topicData = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            intent.getParcelableExtra(EXTRA_TOPIC, Topic::class.java) ?: Topic()
        } else {
            @Suppress("DEPRECATION")
            intent.getParcelableExtra(EXTRA_TOPIC) ?: Topic()
        }
    }

    private fun setColor() {
        if (topicData.color != null) {
            DynamicColors.applyToActivityIfAvailable(
                this,
                DynamicColorsOptions.Builder()
                    .setContentBasedSource(topicData.color!!)
                    .build()
            )
        }
    }

    private fun loadFragment() {
        val bundle = Bundle()
        bundle.putParcelable(PlayTitleFragment.EXTRA_TOPIC, topicData)

        val fragment = PlayTitleFragment()
        fragment.arguments = bundle

        val fragmentManager = supportFragmentManager
        fragmentManager.beginTransaction().apply {
            replace(R.id.fragmentContainer, fragment)
            commit()
        }
    }

    companion object {
        const val EXTRA_TOPIC = "extra_topic"
    }
}