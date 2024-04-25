package dev.maruffirdaus.kuizee.data.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import dev.maruffirdaus.kuizee.data.model.Leaderboard
import dev.maruffirdaus.kuizee.data.model.Topic

@Dao
interface QuizDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(topicData: Topic)

    @Update
    fun update(topicData: Topic)

    @Delete
    fun delete(topicData: Topic)

    @Query("SELECT * FROM topic ORDER BY title ASC")
    suspend fun getAllTopicData(): List<Topic>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(leaderboardData: Leaderboard)

    @Query("DELETE FROM leaderboard")
    fun clearLeaderboardData()

    @Query("SELECT * FROM leaderboard ORDER BY score DESC LIMIT 25")
    suspend fun getLeaderboardData(): List<Leaderboard>
}