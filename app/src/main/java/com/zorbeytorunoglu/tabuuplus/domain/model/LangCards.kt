package com.zorbeytorunoglu.tabuuplus.domain.model

data class LangCards(
    val lang: Lang,
    val cards: Map<Int, Card>
)
