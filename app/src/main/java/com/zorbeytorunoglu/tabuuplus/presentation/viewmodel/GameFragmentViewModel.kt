package com.zorbeytorunoglu.tabuuplus.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zorbeytorunoglu.tabuuplus.domain.model.GameData
import com.zorbeytorunoglu.tabuuplus.domain.model.TeamData
import com.zorbeytorunoglu.tabuuplus.domain.repository.GameRepository
import com.zorbeytorunoglu.tabuuplus.domain.use_case.get_cards_flow_use_case.GetCardsFlowUseCase
import com.zorbeytorunoglu.tabuuplus.domain.util.CountdownManager
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class GameFragmentViewModel @Inject constructor(
    private val getCardsFlowUseCase: GetCardsFlowUseCase,
    private val repository: GameRepository
): ViewModel() {

    val countdownManager: CountdownManager = CountdownManager(
        repository.gameSettings.data?.timeLimit ?: 10,
        viewModelScope
    )

    fun newGame(teamAName: String, teamBName: String) {
        repository.gameData.updateData(
            GameData(TeamData(teamAName, 0), TeamData(teamBName, 0),
                repository.gameSettings.data?.passLimit ?: 3)
        )
    }

}