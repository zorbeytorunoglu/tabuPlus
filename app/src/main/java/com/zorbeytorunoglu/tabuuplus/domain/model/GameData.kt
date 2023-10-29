package com.zorbeytorunoglu.tabuuplus.domain.model

data class GameData(
    val teamAData: TeamData,
    val teamBData: TeamData,
    var passCount: Int
) {
}