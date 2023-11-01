package com.zorbeytorunoglu.tabuuplus.domain.util

import com.zorbeytorunoglu.tabuuplus.domain.model.TeamData

class GameTeamManager(
    private val teamA: TeamData,
    private val teamB: TeamData
) {

    private var currentTeam: TeamData

    init {
        currentTeam = teamA
    }

    fun switchTurn() {
        currentTeam = if (currentTeam == teamA) teamB else teamA
    }

    fun getCurrentTeam(): TeamData = currentTeam

    fun increaseCorrectPoint() {
        getCurrentTeam().correctScore+=1
    }

}