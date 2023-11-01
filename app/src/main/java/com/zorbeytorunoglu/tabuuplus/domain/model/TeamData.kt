package com.zorbeytorunoglu.tabuuplus.domain.model

import java.io.Serializable

data class TeamData(
    val name: String,
    var correctScore: Int = 0,
    var falseScore: Int = 0
): Serializable