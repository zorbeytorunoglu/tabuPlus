package com.zorbeytorunoglu.tabuuplus.data.remote.dto

data class LangCardsDto(
    val language: String,
    val words: Map<String, CardDto>
)
