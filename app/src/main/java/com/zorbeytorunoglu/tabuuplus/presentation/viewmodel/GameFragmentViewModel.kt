package com.zorbeytorunoglu.tabuuplus.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zorbeytorunoglu.tabuuplus.domain.repository.CardRepository
import com.zorbeytorunoglu.tabuuplus.domain.use_case.get_cards_use_case.GetCardsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class GameFragmentViewModel @Inject constructor(
    private val getCardsUseCase: GetCardsUseCase
): ViewModel() {

    fun getCards() {

        val asD = getCardsUseCase(viewModelScope)

    }

}