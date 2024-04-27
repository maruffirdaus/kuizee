package dev.maruffirdaus.kuizee.data.repository

import android.app.Application
import dev.maruffirdaus.kuizee.data.database.QuizDao
import dev.maruffirdaus.kuizee.data.database.QuizRoomDatabase
import dev.maruffirdaus.kuizee.data.model.Leaderboard
import dev.maruffirdaus.kuizee.data.model.Quiz
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class QuizRepository(application: Application) {
    private val mQuizzesDao: QuizDao
    private val executorService: ExecutorService = Executors.newSingleThreadExecutor()

    init {
        val db = QuizRoomDatabase.getDatabase(application)
        mQuizzesDao = db.quizDao()
    }

    fun insert(quizData: Quiz) {
        executorService.execute { mQuizzesDao.insert(quizData) }
    }

    fun update(quizData: Quiz) {
        executorService.execute { mQuizzesDao.update(quizData) }
    }

    fun delete(quizData: Quiz) {
        executorService.execute { mQuizzesDao.delete(quizData) }
    }

    suspend fun getQuizData(): List<Quiz> = mQuizzesDao.getQuizData()

    fun insert(leaderboardData: Leaderboard) {
        executorService.execute { mQuizzesDao.insert(leaderboardData) }
    }

    fun clearLeaderboardData() {
        executorService.execute { mQuizzesDao.clearLeaderboardData() }
    }

    suspend fun getLeaderboardData(): List<Leaderboard> = mQuizzesDao.getLeaderboardData()
}