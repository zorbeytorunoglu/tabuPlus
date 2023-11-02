package com.zorbeytorunoglu.tabuuplus.presentation.viewmodel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zorbeytorunoglu.tabuuplus.R
import com.zorbeytorunoglu.tabuuplus.common.Result
import com.zorbeytorunoglu.tabuuplus.common.State
import com.zorbeytorunoglu.tabuuplus.data.dto.CardsData
import com.zorbeytorunoglu.tabuuplus.domain.repository.CardRepository
import com.zorbeytorunoglu.tabuuplus.domain.use_case.get_cards_flow_use_case.GetCardsFlowUseCase
import com.zorbeytorunoglu.tabuuplus.domain.use_case.get_local_cards_flow_use_case.GetLocalCardsFlowUseCase
import com.zorbeytorunoglu.tabuuplus.presentation.ui.dialog.FirstHelpDialog
import com.zorbeytorunoglu.tabuuplus.presentation.ui.dialog.OnTeamNamesSelectedListener
import com.zorbeytorunoglu.tabuuplus.presentation.ui.dialog.SecondHelpDialog
import com.zorbeytorunoglu.tabuuplus.presentation.ui.dialog.SetupGameDialog
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainFragmentViewModel @Inject constructor(
    private val getLocalCardsFlowUseCase: GetLocalCardsFlowUseCase,
    private val getCardsFlowUseCase: GetCardsFlowUseCase,
    private val cardRepository: CardRepository
): ViewModel() {

    private val _notificationMsgLiveData = MutableLiveData<String>()
    val notificationMsgLiveData: LiveData<String>
        get() = _notificationMsgLiveData

    fun updateCards(context: Context) {

        viewModelScope.launch {
            val localFlow = getLocalCardsFlowUseCase(context)
                .filter { it is Result.Success || it is Result.Error }
                .map { it as Result<CardsData> }

            val remoteFlow = getCardsFlowUseCase(viewModelScope, context, true)
                .filter { it is Result.Success || it is Result.Error }
                .map { it as Result<CardsData> }

            val localResult = localFlow.first()
            val remoteResult = remoteFlow.first()

            if (remoteResult is Result.Success) {
                cardRepository.updateCardsState(State(false, remoteResult.data!!.langCardsList))
            } else {
                if (localResult is Result.Success) {
                    cardRepository.updateCardsState(State(false, localResult.data!!.langCardsList))
                } else {
                    _notificationMsgLiveData.postValue(
                        context.getString(R.string.error_local_cards_unavailable)
                    )
                }
            }

        }

    }

    fun showSetupGameDialog(context: Context, listener: OnTeamNamesSelectedListener) {
        val dialog = SetupGameDialog(context)
        dialog.setListener(listener)
        dialog.show()
    }

    fun showHelpDialog(context: Context) {
        val firstHelpDialog = FirstHelpDialog(context)
        val secondHelpDialog = SecondHelpDialog(context)
        firstHelpDialog.setOnDismissListener {
            secondHelpDialog.show()
        }
        firstHelpDialog.show()
    }

}