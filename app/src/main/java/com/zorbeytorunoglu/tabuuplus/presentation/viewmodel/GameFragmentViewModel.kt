package com.zorbeytorunoglu.tabuuplus.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zorbeytorunoglu.tabuuplus.domain.model.Card
import com.zorbeytorunoglu.tabuuplus.domain.model.GameData
import com.zorbeytorunoglu.tabuuplus.domain.model.TeamData
import com.zorbeytorunoglu.tabuuplus.domain.repository.CardRepository
import com.zorbeytorunoglu.tabuuplus.domain.repository.GameRepository
import com.zorbeytorunoglu.tabuuplus.domain.util.CountdownManager
import com.zorbeytorunoglu.tabuuplus.domain.util.GameCardManager
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class GameFragmentViewModel @Inject constructor(
    private val gameRepository: GameRepository,
    private val cardRepository: CardRepository
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
        gameRepository.gameSettings.data?.timeLimit ?: 10,
        viewModelScope
    )

    val gameCardManager = GameCardManager(gameRepository, cardRepository)

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

    fun onTourEnd() {

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

    private fun increaseCorrectPoint() {
        increaseNPostTeamScoresBy(1, 0)
    }

    private fun increaseFalsePoint() {
        increaseNPostTeamScoresBy(0, 1)
    }

    private fun postNewCard() {
        _currentCardLiveData.postValue(gameCardManager.pick())
    }

    private fun increaseNPostTeamScoresBy(correctPoint: Int, falsePoint: Int) {
        val currentData = _currentTeamLiveData.value!!
        _currentTeamLiveData.postValue(TeamData(
            currentData.name,
            currentData.correctScore+correctPoint,
            currentData.falseScore+falsePoint
        ))
    }

    fun newGame(teamAName: String, teamBName: String) {
        gameRepository.gameData.updateData(
            GameData(TeamData(teamAName), TeamData(teamBName),
                gameRepository.gameSettings.data?.passLimit ?: 3)
        )
    }

}