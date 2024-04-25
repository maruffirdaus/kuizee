package dev.maruffirdaus.kuizee.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import dev.maruffirdaus.kuizee.data.model.Leaderboard
import dev.maruffirdaus.kuizee.data.model.Topic

@Database(entities = [Topic::class, Leaderboard::class], version = 1, exportSchema = false)
@TypeConverters(Converters::class)
abstract class QuizRoomDatabase : RoomDatabase() {
    abstract fun quizDao(): QuizDao

    companion object {
        @Volatile
        private var INSTANCE: QuizRoomDatabase? = null

        @JvmStatic
        fun getDatabase(context: Context): QuizRoomDatabase {
            if (INSTANCE == null) {
                synchronized(QuizRoomDatabase::class.java) {
                    INSTANCE = Room.databaseBuilder(
                        context.applicationContext,
                        QuizRoomDatabase::class.java,
                        "quiz_database"
                    ).build()
                }
            }
            return INSTANCE as QuizRoomDatabase
        }
    }
}