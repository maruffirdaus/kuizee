package dev.maruffirdaus.kuizee.ui.main.activity

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.ViewGroup.MarginLayoutParams
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.updateLayoutParams
import androidx.core.view.updatePadding
import com.google.android.gms.oss.licenses.OssLicensesMenuActivity
import dev.maruffirdaus.kuizee.databinding.ActivityAppInfoBinding

class AppInfoActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAppInfoBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityAppInfoBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setInsets()
        setButton()
    }

    private fun setInsets() {
        ViewCompat.setOnApplyWindowInsetsListener(binding.appBar) { v, windowInsets ->
            val insets = windowInsets.getInsets(WindowInsetsCompat.Type.systemBars())

            v.updateLayoutParams<MarginLayoutParams> {
                topMargin = insets.top
            }

            WindowInsetsCompat.CONSUMED
        }

        ViewCompat.setOnApplyWindowInsetsListener(binding.bottomInset) { v, windowInsets ->
            val insets = windowInsets.getInsets(WindowInsetsCompat.Type.systemBars())

            v.updatePadding(bottom = insets.bottom)

            WindowInsetsCompat.CONSUMED
        }
    }

    private fun setButton() {
        with(binding) {
            appBar.setNavigationOnClickListener {
                finish()
            }

            gitHubButton.setOnClickListener {
                val openURL = Intent(Intent.ACTION_VIEW)
                openURL.data = Uri.parse("https://github.com/maruffirdaus")
                startActivity(openURL)
            }

            licencesButton.setOnClickListener {
                startActivity(Intent(this@AppInfoActivity, OssLicensesMenuActivity::class.java))
            }
        }
    }
}