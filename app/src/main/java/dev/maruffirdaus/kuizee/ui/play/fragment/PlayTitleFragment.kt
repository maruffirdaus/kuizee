package dev.maruffirdaus.kuizee.ui.play.fragment

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.net.toUri
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.updatePadding
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import dev.maruffirdaus.kuizee.R
import dev.maruffirdaus.kuizee.data.model.Play
import dev.maruffirdaus.kuizee.data.model.SidebarData
import dev.maruffirdaus.kuizee.data.model.Topic
import dev.maruffirdaus.kuizee.databinding.FragmentPlayTitleBinding

class PlayTitleFragment : Fragment() {
    private lateinit var binding: FragmentPlayTitleBinding
    private lateinit var topicData: Topic

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentPlayTitleBinding.inflate(layoutInflater)
        getData()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setInsets()
        setContent()
        setButton()
    }

    private fun getData() {
        topicData = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            arguments?.getParcelable(EXTRA_TOPIC, Topic::class.java) ?: Topic()
        } else {
            @Suppress("DEPRECATION")
            arguments?.getParcelable(EXTRA_TOPIC) ?: Topic()
        }
    }

    private fun loadFragment(fragment: Fragment) {
        val fragmentManager = parentFragmentManager
        fragmentManager.beginTransaction().apply {
            replace(R.id.fragmentContainer, fragment)
            addToBackStack("PlayTitleFragment")
            commit()
        }
    }

    private fun setInsets() {
        ViewCompat.setOnApplyWindowInsetsListener(binding.bottomInset) { v, windowInsets ->
            val insets = windowInsets.getInsets(WindowInsetsCompat.Type.systemBars())

            v.updatePadding(bottom = insets.bottom)

            WindowInsetsCompat.CONSUMED
        }
    }

    private fun setContent() {
        with(binding) {
            if (topicData.img != null) {
                context?.let {
                    Glide.with(it)
                        .load(topicData.img)
                        .into(backgroundHeader)
                }
                backgroundHeader.alpha = 0.3F
            }
            title.text = topicData.title
            contentDescription.text = topicData.desc
        }
    }

    private fun setButton() {
        with(binding) {
            playButton.setOnClickListener {
                val bundle = Bundle()
                var questionStatus = arrayOf<SidebarData>()

                for (i in topicData.questions) {
                    questionStatus += SidebarData()
                }

                if (topicData.questions[0].type == 0) {
                    bundle.putParcelable(
                        PlayMultipleChoiceFragment.EXTRA_PLAY,
                        Play(
                            topicData.img?.toUri(),
                            topicData.title,
                            topicData.questions,
                            questionStatus
                        )
                    )

                    val fragment = PlayMultipleChoiceFragment()
                    fragment.arguments = bundle

                    loadFragment(fragment)
                } else {
                    bundle.putParcelable(
                        PlayShortFormFragment.EXTRA_PLAY,
                        Play(
                            topicData.img?.toUri(),
                            topicData.title,
                            topicData.questions,
                            questionStatus
                        )
                    )

                    val fragment = PlayShortFormFragment()
                    fragment.arguments = bundle

                    loadFragment(fragment)
                }
            }

            cancelButton.setOnClickListener {
                activity?.finish()
            }
        }
    }

    companion object {
        const val EXTRA_TOPIC = "extra_topic"
    }
}