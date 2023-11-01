package com.zorbeytorunoglu.tabuuplus.presentation.viewmodel

import android.app.Dialog
import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zorbeytorunoglu.tabuuplus.domain.model.Card
import com.zorbeytorunoglu.tabuuplus.domain.model.TeamData
import com.zorbeytorunoglu.tabuuplus.domain.model.TurnData
import com.zorbeytorunoglu.tabuuplus.domain.repository.CardRepository
import com.zorbeytorunoglu.tabuuplus.domain.repository.GameRepository
import com.zorbeytorunoglu.tabuuplus.domain.util.CountdownManager
import com.zorbeytorunoglu.tabuuplus.domain.util.GameCardManager
import com.zorbeytorunoglu.tabuuplus.presentation.ui.dialog.TurnEndDialog
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

interface OnGameEndListener {
    fun onGameEnd(winnerTeam: TeamData, teamA: TeamData, teamB: TeamData)
}

@HiltViewModel
class GameFragmentViewModel @Inject constructor(
    private val gameRepository: GameRepository,
    cardRepository: CardRepository
): ViewModel() {

    private val _currentCardLiveData = MutableLiveData<Card>()
    val cardLiveData: LiveData<Card> = _currentCardLiveData

    private val _currentTeamLiveData = MutableLiveData<TeamData>()
    val currentTeamLiveData = _currentTeamLiveData

    private val _passCountLiveData = MutableLiveData(
        gameRepository.gameSettings.data?.passLimit ?: 3
    )
    val passCountLiveData = _passCountLiveData

    val countdownManager: CountdownManager = CountdownManager(
        gameRepository.gameSettings.data?.timeLimit ?: 30,
        viewModelScope
    ) {
        onTurnEnd()
    }

    private val turnData = TurnData(0, 0)

    private val gameCardManager = GameCardManager(gameRepository, cardRepository)

    private lateinit var teamA: TeamData
    private lateinit var teamB: TeamData

    private var listener: OnGameEndListener? = null

    init {
        gameCardManager.loadCards()
    }

    fun onCorrect() {
        increaseCorrectPoint()
        postNewCard()
    }

    fun onFalse() {
        increaseFalsePoint()
        postNewCard()
    }

    fun onTurnEnd() {
        switchTeamTurn()
        resetTurnData()
        resetPassCount()
        postNewCard()
        countdownManager.start()
    }

    fun onPass(): Boolean {
        return if (_passCountLiveData.value!! <= 0) {
            false
        } else {
            _passCountLiveData.postValue(_passCountLiveData.value!! - 1)
            postNewCard()
            true
        }
    }

    fun onGameEnd() {
        countdownManager.stop()
        listener?.onGameEnd(getCurrentTeam(), teamA, teamB)
    }

    fun startGame() {
        postNewCard()
        countdownManager.start()
        _currentTeamLiveData.postValue(teamA)
    }

    private fun postNewCard() {
        _currentCardLiveData.postValue(gameCardManager.pick())
    }

    fun setTeams(teamAName: String, teamBName: String) {
        teamA = TeamData(teamAName)
        teamB = TeamData(teamBName)
    }

    fun getTurnEndDialog(context: Context): Dialog {
        return TurnEndDialog(context, teamA, teamB, turnData)
    }

    fun setOnGameEndListener(listener: OnGameEndListener) {
        this.listener = listener
    }

    private fun switchTeamTurn() {
        val currentTeam = if (_currentTeamLiveData.value!! == teamA) teamB else teamA
        _currentTeamLiveData.postValue(currentTeam)
    }

    private fun resetPassCount() {
        _passCountLiveData.postValue(gameRepository.gameSettings.data?.passLimit ?: 3)
    }

    private fun resetTurnData() {
        turnData.correctPoint = 0
        turnData.falsePoint = 0
    }

    private fun getCurrentTeam(): TeamData {
        return _currentTeamLiveData.value!!
    }

    private fun increaseCorrectPoint() {
        getCurrentTeam().correctScore++
        _currentTeamLiveData.postValue(getCurrentTeam())
        turnData.correctPoint++

        if ((gameRepository.gameSettings.data?.winningPoint
                ?: 25) == getCurrentTeam().correctScore - getCurrentTeam().falseScore
        ) {
            onGameEnd()
        }
    }

    private fun increaseFalsePoint() {
        getCurrentTeam().falseScore++
        _currentTeamLiveData.postValue(getCurrentTeam())
        turnData.falsePoint++
    }

}