package dev.maruffirdaus.kuizee.data.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import dev.maruffirdaus.kuizee.data.model.Leaderboard
import dev.maruffirdaus.kuizee.data.model.Quiz

@Dao
interface QuizDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(quizData: Quiz)

    @Update
    fun update(quizData: Quiz)

    @Delete
    fun delete(quizData: Quiz)

    @Query("SELECT * FROM quiz ORDER BY title ASC")
    suspend fun getQuizData(): List<Quiz>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(leaderboardData: Leaderboard)

    @Query("DELETE FROM leaderboard")
    fun clearLeaderboardData()

    @Query("SELECT * FROM leaderboard ORDER BY score DESC LIMIT 25")
    suspend fun getLeaderboardData(): List<Leaderboard>
}