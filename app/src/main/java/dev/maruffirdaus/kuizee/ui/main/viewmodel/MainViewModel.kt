package dev.maruffirdaus.kuizee.ui.main.viewmodel

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import dev.maruffirdaus.kuizee.data.model.Leaderboard
import dev.maruffirdaus.kuizee.data.model.Topic
import dev.maruffirdaus.kuizee.data.repository.QuizRepository

class MainViewModel(application: Application) : ViewModel() {
    val mQuizRepository: QuizRepository = QuizRepository(application)

    private val _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean> = _loading

    private val _listTopic = MutableLiveData<List<Topic>>()
    lateinit var listTopic: LiveData<List<Topic>>

    private val _listLeaderboard = MutableLiveData<List<Leaderboard>>()
    lateinit var listLeaderboard: LiveData<List<Leaderboard>>

    private val _deleteButtonStatus = MutableLiveData<Boolean>()
    val deleteButtonStatus: LiveData<Boolean> = _deleteButtonStatus

    fun setLoading(status: Boolean) {
        _loading.value = status
    }

    fun deleteTopicData(selectedItems: IntArray) {
        for (i in selectedItems) {
            listTopic.value?.get(i)?.let { mQuizRepository.delete(it) }
        }
    }

    fun getAllTopicData() {
        listTopic = liveData {
            _loading.value = true
            emit(mQuizRepository.getAllTopicData())
            _loading.value = false
        }
    }

    fun replaceTopicData(topicData: List<Topic>) {
        _listTopic.value = topicData
        listTopic = _listTopic
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