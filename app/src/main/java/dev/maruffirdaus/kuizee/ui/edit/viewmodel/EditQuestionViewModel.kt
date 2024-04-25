package dev.maruffirdaus.kuizee.ui.edit.viewmodel

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dev.maruffirdaus.kuizee.data.model.Question
import dev.maruffirdaus.kuizee.data.model.Topic
import dev.maruffirdaus.kuizee.data.repository.QuizRepository

class EditQuestionViewModel(application: Application) : ViewModel() {
    private val mQuizRepository: QuizRepository = QuizRepository(application)

    fun insert(topicData: Topic) {
        mQuizRepository.insert(topicData)
    }

    fun update(topicData: Topic) {
        mQuizRepository.update(topicData)
    }

    private val _question = MutableLiveData<List<Question>>()
    val listQuestion: LiveData<List<Question>> = _question

    private val _deleteButtonStatus = MutableLiveData<Boolean>()
    val deleteButtonStatus: LiveData<Boolean> = _deleteButtonStatus

    fun insertQuestion(questionData: Array<Question>) {
        _question.value = questionData.toList()
    }

    fun changeDeleteButtonStatus(deleteButtonStatus: Boolean) {
        _deleteButtonStatus.value = deleteButtonStatus
    }

    fun getDeleteButtonStatus(): Boolean {
        return deleteButtonStatus.value ?: false
    }
}