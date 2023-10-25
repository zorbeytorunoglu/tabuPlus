package com.zorbeytorunoglu.tabuuplus.presentation.viewmodel

import androidx.lifecycle.ViewModel
import com.zorbeytorunoglu.tabuuplus.domain.repository.CardRepository
import com.zorbeytorunoglu.tabuuplus.domain.use_case.get_cards_use_case.GetCardsUseCase
import com.zorbeytorunoglu.tabuuplus.domain.use_case.get_local_cards_use_case.GetLocalCardsUseCase
import com.zorbeytorunoglu.tabuuplus.domain.use_case.save_cards_use_case.SaveCardsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel

@HiltViewModel
class SettingsFragmentViewModel constructor(
    private val getCardsUseCase: GetCardsUseCase,
    private val getLocalCardsUseCase: GetLocalCardsUseCase,
    private val saveCardsUseCase: SaveCardsUseCase,
    private val cardsRepository: CardRepository
): ViewModel() {

    val stateFlow = cardsRepository.langCardsState



}