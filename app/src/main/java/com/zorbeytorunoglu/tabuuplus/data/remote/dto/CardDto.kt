package com.zorbeytorunoglu.tabuuplus.data.remote.dto

import com.google.gson.annotations.SerializedName

data class CardDto(
    val word: String,
    @SerializedName("related_words")
    val relatedWords: List<String>
) {
}