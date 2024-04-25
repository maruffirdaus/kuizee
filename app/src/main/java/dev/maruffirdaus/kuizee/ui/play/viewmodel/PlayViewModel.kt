package dev.maruffirdaus.kuizee.ui.play.viewmodel

import android.app.Application
import androidx.lifecycle.ViewModel
import dev.maruffirdaus.kuizee.data.model.Leaderboard
import dev.maruffirdaus.kuizee.data.repository.QuizRepository

class PlayViewModel(application: Application) : ViewModel() {
    private val mQuizRepository: QuizRepository = QuizRepository(application)

    fun insert(leaderboardData: Leaderboard) {
        mQuizRepository.insert(leaderboardData)
    }
}