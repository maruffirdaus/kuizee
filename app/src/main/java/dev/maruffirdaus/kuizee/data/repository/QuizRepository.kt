package dev.maruffirdaus.kuizee.data.repository

import android.app.Application
import dev.maruffirdaus.kuizee.data.database.QuizDao
import dev.maruffirdaus.kuizee.data.database.QuizRoomDatabase
import dev.maruffirdaus.kuizee.data.model.Leaderboard
import dev.maruffirdaus.kuizee.data.model.Topic
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class QuizRepository(application: Application) {
    private val mQuizzesDao: QuizDao
    private val executorService: ExecutorService = Executors.newSingleThreadExecutor()

    init {
        val db = QuizRoomDatabase.getDatabase(application)
        mQuizzesDao = db.quizDao()
    }

    fun insert(topicData: Topic) {
        executorService.execute { mQuizzesDao.insert(topicData) }
    }

    fun update(topicData: Topic) {
        executorService.execute { mQuizzesDao.update(topicData) }
    }

    fun delete(topicData: Topic) {
        executorService.execute { mQuizzesDao.delete(topicData) }
    }

    suspend fun getAllTopicData(): List<Topic> = mQuizzesDao.getAllTopicData()

    fun insert(leaderboardData: Leaderboard) {
        executorService.execute { mQuizzesDao.insert(leaderboardData) }
    }

    fun clearLeaderboardData() {
        executorService.execute { mQuizzesDao.clearLeaderboardData() }
    }

    suspend fun getLeaderboardData(): List<Leaderboard> = mQuizzesDao.getLeaderboardData()
}