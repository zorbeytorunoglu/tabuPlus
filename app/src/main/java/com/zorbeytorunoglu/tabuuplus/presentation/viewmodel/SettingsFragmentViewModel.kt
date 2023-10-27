package com.zorbeytorunoglu.tabuuplus.presentation.viewmodel

import android.content.Context
import android.net.ConnectivityManager
import android.util.Log
import android.widget.EditText
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.material.textfield.TextInputLayout
import com.zorbeytorunoglu.tabuuplus.common.Result
import com.zorbeytorunoglu.tabuuplus.common.State
import com.zorbeytorunoglu.tabuuplus.domain.model.GameSettings
import com.zorbeytorunoglu.tabuuplus.domain.repository.CardRepository
import com.zorbeytorunoglu.tabuuplus.domain.repository.GameRepository
import com.zorbeytorunoglu.tabuuplus.domain.use_case.get_cards_flow_use_case.GetCardsFlowUseCase
import com.zorbeytorunoglu.tabuuplus.presentation.ui.dialog.LoadingDialog
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.sync.Mutex
import javax.inject.Inject

@HiltViewModel
class SettingsFragmentViewModel @Inject constructor(
    private val getCardsFlowUseCase: GetCardsFlowUseCase,
    private val cardRepository: CardRepository,
    private val gameRepository: GameRepository
): ViewModel() {

    private val _notificationMsgLiveData = MutableLiveData<String>()
    val notificationMsgLiveData: LiveData<String>
        get() = _notificationMsgLiveData

    fun updateCards(context: Context, saveLocal: Boolean, loadingDialog: LoadingDialog?, updateData: Boolean) {

        if (!isInternetAvailable(context)) {
            _notificationMsgLiveData.postValue("You have no internet connection.")
            return
        }

        getCardsFlowUseCase(viewModelScope, context, saveLocal).onEach { result ->

            when (result) {
                is Result.Loading -> {
                    loadingDialog?.show()
                }
                is Result.Error -> {
                    loadingDialog?.dismiss()
                    _notificationMsgLiveData.postValue(result.message ?: "Could not update the cards. Try again later.")
                }
                is Result.Success -> {
                    loadingDialog?.dismiss()
                    cardRepository.updateCardsState(State(false, result.data!!.langCardsList))
                    if (updateData)
                        _notificationMsgLiveData.postValue("Latest cards are downloaded!")
                }
            }

        }.launchIn(viewModelScope)

    }

    private fun isInternetAvailable(context: Context): Boolean {
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        return connectivityManager.activeNetwork != null
    }

    fun validateNSaveForm(
        timeLimitET: EditText,
        timeLimitLayout: TextInputLayout,
        passLimitET: EditText,
        passLimitLayout: TextInputLayout,
        falsePenaltyET: EditText,
        falsePenaltyLayout: TextInputLayout,
        winningPointET: EditText,
        winningPointLayout: TextInputLayout
    ): Boolean {

        var valid = true

        val timeLimit = kotlin.runCatching { timeLimitET.text.toString().toInt() }.getOrNull() ?: kotlin.run {
            timeLimitLayout.error = "Not valid time limit."
            return false
        }

        if (timeLimit > 240 || timeLimit < 20) {
            timeLimitLayout.error = "Time limit must be between 20 and 240"
        }

        val passLimit = kotlin.runCatching { passLimitET.text.toString().toInt() }.getOrNull() ?: kotlin.run {
            passLimitLayout.error = "Not valid pass limit."
            return false
        }

        if (passLimit > 6 || passLimit < 0) {
            passLimitLayout.error = "Pass limit must be between 0 and 6"
            return false
        }

        val falsePenalty = kotlin.runCatching { falsePenaltyET.text.toString().toInt() }.getOrNull() ?: kotlin.run {
            falsePenaltyLayout.error = "Not valid false penalty point."
            return false
        }

        if (falsePenalty > 200 || falsePenalty < 0) {
            falsePenaltyLayout.error = "Must be between 0 and 200."
            return false
        }

        val winningPoint = kotlin.runCatching { winningPointET.text.toString().toInt() }.getOrNull() ?: kotlin.run {
            winningPointLayout.error = "Not valid winning point."
            return false
        }

        if (winningPoint > 1000 || winningPoint < 20) {
            winningPointLayout.error = "Must be between 20 and 1000."
            return false
        }

        gameRepository.gameSettings.updateData(
            GameSettings(timeLimit, passLimit, falsePenalty, winningPoint)
        )

        return true

    }

}