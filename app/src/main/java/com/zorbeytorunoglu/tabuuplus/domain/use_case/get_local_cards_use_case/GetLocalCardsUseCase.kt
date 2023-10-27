package com.zorbeytorunoglu.tabuuplus.domain.use_case.get_local_cards_use_case

import android.content.Context
import com.zorbeytorunoglu.tabuuplus.common.Result
import com.zorbeytorunoglu.tabuuplus.common.State
import com.zorbeytorunoglu.tabuuplus.data.CardsJsonParser
import com.zorbeytorunoglu.tabuuplus.data.dto.CardsData
import com.zorbeytorunoglu.tabuuplus.domain.model.LangCards
import com.zorbeytorunoglu.tabuuplus.domain.repository.CardRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import retrofit2.HttpException
import java.io.File
import java.io.IOException
import javax.inject.Inject

class GetLocalCardsUseCase @Inject constructor(
    private val repository: CardRepository
) {

    operator fun invoke(scope: CoroutineScope, context: Context) {

        flow {

            try {
                emit(Result.Loading())
                val file = File(context.filesDir, "cards.json")
                if (!file.exists() || !file.isFile) {
                    emit(Result.Error("File does not exists."))
                } else {
                    val data = CardsJsonParser(file.readText()).parse().langCardsList
                    if (data.isNullOrEmpty()) {
                        emit(Result.Error("Data is null or empty."))
                    } else {
                        emit(Result.Success(data))
                    }
                }
            } catch (e: HttpException) {
                emit(Result.Error(e.localizedMessage ?: "An unexpected error occurred."))
            } catch (e: IOException) {
                emit(Result.Error("Couldn't reach web service. Check your internet connection."))
            }

        }.onEach { result ->

            when (result) {
                is Result.Success -> {
                    repository.updateCardsState(
                        State(false, result.data, null)
                    )
                }
                is Result.Error -> {
                    repository.updateCardsState(
                        State(false, null, result.message)
                    )
                }
                is Result.Loading -> {
                    repository.updateCardsState(
                        State(true, null, null)
                    )
                }
            }

        }.launchIn(scope)

    }

}