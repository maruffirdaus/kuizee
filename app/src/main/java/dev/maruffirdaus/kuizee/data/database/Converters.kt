package dev.maruffirdaus.kuizee.data.database

import androidx.room.TypeConverter
import com.google.gson.Gson
import dev.maruffirdaus.kuizee.data.model.Question

class Converters {
    @TypeConverter
    fun fromArray(value: Array<Question>): String = Gson().toJson(value)

    @TypeConverter
    fun toArray(value: String): Array<Question> = Gson().fromJson(value, Array<Question>::class.java)
}