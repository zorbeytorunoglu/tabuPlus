package com.zorbeytorunoglu.tabuuplus.domain.repository

import com.zorbeytorunoglu.tabuuplus.common.State
import com.zorbeytorunoglu.tabuuplus.domain.model.LangCards
import kotlinx.coroutines.flow.StateFlow
import okhttp3.ResponseBody
import retrofit2.Response

interface CardRepository {

    val langCardsState: StateFlow<State<List<LangCards>>>

    suspend fun getRemoteCards(): Response<ResponseBody>

    fun updateCardsState(state: State<List<LangCards>>)

}