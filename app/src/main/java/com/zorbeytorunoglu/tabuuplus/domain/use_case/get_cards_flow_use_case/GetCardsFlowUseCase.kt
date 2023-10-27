package com.zorbeytorunoglu.tabuuplus.domain.use_case.get_cards_flow_use_case

import android.content.Context
import com.zorbeytorunoglu.tabuuplus.common.Result
import com.zorbeytorunoglu.tabuuplus.data.CardsJsonParser
import com.zorbeytorunoglu.tabuuplus.domain.repository.CardRepository
import com.zorbeytorunoglu.tabuuplus.domain.use_case.save_cards_use_case.SaveCardsUseCase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class GetCardsFlowUseCase @Inject constructor(
    private val saveCardsUseCase: SaveCardsUseCase,
    private val repository: CardRepository
) {

    operator fun invoke(scope: CoroutineScope, context: Context, saveLocal: Boolean) = flow {

        try {
            emit(Result.Loading())
            val response = repository.getRemoteCards()
            if (!response.isSuccessful || response.body() == null) {
                emit(Result.Error("Response was not successful."))
            } else {
                val json = response.body()!!.string()
                val parser = CardsJsonParser(json)
                val data = parser.parse()
                if (data.langCardsList.isNullOrEmpty()) {
                    emit(Result.Error("Parsed data's card list is empty."))
                } else {
                    emit(Result.Success(data))
                    if (saveLocal)
                        saveCardsUseCase(scope, context, json)
                }
            }
        } catch (e: HttpException) {
            emit(Result.Error(e.localizedMessage ?: "An unexpected error occurred."))
        } catch (e: IOException) {
            emit(Result.Error("Couldn't reach web service. Check your internet connection."))
        }
    }
}