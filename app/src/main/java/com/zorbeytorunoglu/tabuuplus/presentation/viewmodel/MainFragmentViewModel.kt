package com.zorbeytorunoglu.tabuuplus.presentation.viewmodel

import android.content.Context
import android.net.ConnectivityManager
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zorbeytorunoglu.tabuuplus.common.Result
import com.zorbeytorunoglu.tabuuplus.common.State
import com.zorbeytorunoglu.tabuuplus.data.dto.CardsData
import com.zorbeytorunoglu.tabuuplus.domain.repository.CardRepository
import com.zorbeytorunoglu.tabuuplus.domain.use_case.get_cards_flow_use_case.GetCardsFlowUseCase
import com.zorbeytorunoglu.tabuuplus.domain.use_case.get_local_cards_flow_use_case.GetLocalCardsFlowUseCase
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
                Log.i("Tabu", "Remote cards are downloaded an will be used.")
                cardRepository.updateCardsState(State(false, remoteResult.data!!.langCardsList))
            } else {
                Log.i("Tabu", "Couldn't reach to remote database.")
                if (localResult is Result.Success) {
                    Log.i("Tabu", "Local file is found and will be used.")
                    cardRepository.updateCardsState(State(false, localResult.data!!.langCardsList))
                } else {
                    Log.i("Tabu", "Both remote and local data couldn't found.")
                    _notificationMsgLiveData.postValue("Local cards file doesn't exist and couldn't reach to remote database. Check your internet connection.")
                }
            }

        }

    }

}