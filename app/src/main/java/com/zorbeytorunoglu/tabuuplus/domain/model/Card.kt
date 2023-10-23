package com.zorbeytorunoglu.tabuuplus.domain.model

data class Card(
    val mainWord: String,
    val relatedWords: List<String>
) {
}