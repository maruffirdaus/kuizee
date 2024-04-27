package dev.maruffirdaus.kuizee.ui.main.fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import dev.maruffirdaus.kuizee.R
import dev.maruffirdaus.kuizee.data.model.Quiz
import dev.maruffirdaus.kuizee.databinding.FragmentPlayBinding
import dev.maruffirdaus.kuizee.ui.helper.ViewModelFactory
import dev.maruffirdaus.kuizee.ui.main.activity.AppInfoActivity
import dev.maruffirdaus.kuizee.ui.main.adapter.PlayAdapter
import dev.maruffirdaus.kuizee.ui.main.viewmodel.MainViewModel
import dev.maruffirdaus.kuizee.ui.play.activity.PlayActivity

class PlayFragment : Fragment() {
    private lateinit var binding: FragmentPlayBinding
    private lateinit var viewModel: MainViewModel
    private val adapter = PlayAdapter()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentPlayBinding.inflate(layoutInflater)
        obtainViewModel(requireActivity() as AppCompatActivity)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getQuizData()
        viewModel.getLeaderboardData()
        observeData()
        setRecyclerView()
        setOnClickAdapter()
        setButton()
    }

    private fun obtainViewModel(activity: AppCompatActivity) {
        val factory = ViewModelFactory.getInstance(activity.application)
        viewModel = ViewModelProvider(activity, factory)[MainViewModel::class.java]
    }

    private fun observeData() {
        viewModel.loading.observe(viewLifecycleOwner) {
            setLoading(it)
        }
        viewModel.listQuiz.observe(viewLifecycleOwner) {
            adapter.setListQuiz(it)
            if (it.isEmpty()) {
                binding.emptyScreen.visibility = View.VISIBLE
            } else {
                binding.emptyScreen.visibility = View.GONE
            }
        }
    }

    private fun setLoading(status: Boolean) {
        if (status) {
            binding.loadingScreen.visibility = View.VISIBLE
        } else {
            binding.loadingScreen.visibility = View.GONE
        }
    }

    private fun setRecyclerView() {
        with(binding) {
            val layoutManager = LinearLayoutManager(requireContext())
            recyclerView.layoutManager = layoutManager
            recyclerView.adapter = adapter
        }
    }

    private fun setOnClickAdapter() {
        adapter.setOnItemClickCallback(object : PlayAdapter.OnItemClickCallback {
            override fun onItemClicked(quizData: Quiz) {
                val intent = Intent(requireActivity(), PlayActivity::class.java)
                intent.putExtra(PlayActivity.EXTRA_QUIZ, quizData)

                startActivity(intent)
            }
        })
    }

    private fun setButton() {
        with(binding) {
            appBar.setOnMenuItemClickListener {
                when (it.itemId) {
                    R.id.appInfo -> {
                        startActivity(Intent(requireActivity(), AppInfoActivity::class.java))
                        true
                    }

                    else -> false
                }
            }
        }
    }
}