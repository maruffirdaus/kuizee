package dev.maruffirdaus.kuizee.data.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Answer(
    var answer: String = "default",
    var isCorrect: Boolean = false
) : Parcelable
