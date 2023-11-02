package com.zorbeytorunoglu.tabuuplus.data.repository

import com.zorbeytorunoglu.tabuuplus.common.State
import com.zorbeytorunoglu.tabuuplus.data.remote.TabuApi
import com.zorbeytorunoglu.tabuuplus.domain.model.LangCards
import com.zorbeytorunoglu.tabuuplus.domain.repository.CardRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import okhttp3.ResponseBody
import retrofit2.Response
import javax.inject.Inject

class CardRepositoryImpl @Inject constructor(
    private val api: TabuApi
): CardRepository {

    private val _langCardsState = MutableStateFlow(State<List<LangCards>>())
    override val langCardsState: StateFlow<State<List<LangCards>>>
        get() = _langCardsState

    override suspend fun getRemoteCards(): Response<ResponseBody> = api.getCards()

    override fun updateCardsState(state: State<List<LangCards>>) {
        _langCardsState.update { state }
    }

}