package com.zorbeytorunoglu.tabuuplus.presentation.viewmodel

import androidx.lifecycle.ViewModel
import com.zorbeytorunoglu.tabuuplus.domain.use_case.get_cards_flow_use_case.GetCardsFlowUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class GameFragmentViewModel @Inject constructor(
    private val getCardsFlowUseCase: GetCardsFlowUseCase
): ViewModel() {

    fun getCards() {



    }

}