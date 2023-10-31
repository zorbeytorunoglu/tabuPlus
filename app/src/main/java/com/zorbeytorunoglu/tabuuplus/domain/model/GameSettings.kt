package com.zorbeytorunoglu.tabuuplus.domain.model

data class GameSettings(
    val cardLang: Lang,
    val timeLimit: Int,
    val passLimit: Int,
    val falsePointPenalty: Int,
    val winningPoint: Int
)