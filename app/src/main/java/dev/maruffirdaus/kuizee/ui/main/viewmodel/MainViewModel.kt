package dev.maruffirdaus.kuizee.ui.main.viewmodel

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import dev.maruffirdaus.kuizee.data.model.Leaderboard
import dev.maruffirdaus.kuizee.data.model.Quiz
import dev.maruffirdaus.kuizee.data.repository.QuizRepository

class MainViewModel(application: Application) : ViewModel() {
    val mQuizRepository: QuizRepository = QuizRepository(application)

    private val _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean> = _loading

    private val _listQuiz = MutableLiveData<List<Quiz>>()
    lateinit var listQuiz: LiveData<List<Quiz>>

    private val _listLeaderboard = MutableLiveData<List<Leaderboard>>()
    lateinit var listLeaderboard: LiveData<List<Leaderboard>>

    private val _deleteButtonStatus = MutableLiveData<Boolean>()
    val deleteButtonStatus: LiveData<Boolean> = _deleteButtonStatus

    fun setLoading(status: Boolean) {
        _loading.value = status
    }

    fun deleteQuizData(selectedItems: IntArray) {
        for (i in selectedItems) {
            listQuiz.value?.get(i)?.let { mQuizRepository.delete(it) }
        }
    }

    fun getQuizData() {
        listQuiz = liveData {
            _loading.value = true
            emit(mQuizRepository.getQuizData())
            _loading.value = false
        }
    }

    fun replaceTopicData(quizData: List<Quiz>) {
        _listQuiz.value = quizData
        listQuiz = _listQuiz
    }

    fun clearLeaderboardData() {
        mQuizRepository.clearLeaderboardData()
    }

    fun getLeaderboardData() {
        listLeaderboard = liveData {
            _loading.value = true
            emit(mQuizRepository.getLeaderboardData())
            _loading.value = false
        }
    }

    fun replaceLeaderboardData(leaderboardData: List<Leaderboard>) {
        _listLeaderboard.value = leaderboardData
        listLeaderboard = _listLeaderboard
    }

    fun changeDeleteButtonStatus(deleteButtonStatus: Boolean) {
        _deleteButtonStatus.value = deleteButtonStatus
    }

    fun getDeleteButtonStatus(): Boolean {
        return deleteButtonStatus.value ?: false
    }
}