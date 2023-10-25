package com.zorbeytorunoglu.tabuuplus.domain.use_case.get_cards_use_case

import com.zorbeytorunoglu.tabuuplus.common.Result
import com.zorbeytorunoglu.tabuuplus.common.State
import com.zorbeytorunoglu.tabuuplus.data.CardsJsonParser
import com.zorbeytorunoglu.tabuuplus.domain.model.LangCards
import com.zorbeytorunoglu.tabuuplus.domain.repository.CardRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class GetCardsUseCase @Inject constructor(
    private val repository: CardRepository
) {

    operator fun invoke(scope: CoroutineScope) {

        flow<Result<List<LangCards>>> {

            try {
                emit(Result.Loading<List<LangCards>>())
                val response = repository.getCards()
                if (!response.isSuccessful || response.body() == null) {
                    emit(Result.Error<List<LangCards>>("Response was not successful."))
                } else {
                    val parser = CardsJsonParser(response.body()!!.string())
                    val data = parser.parse()
                    if (data.isEmpty())
                        emit(Result.Error<List<LangCards>>("Parsed data is empty."))
                    else
                        emit(Result.Success<List<LangCards>>(data))
                }
            } catch (e: HttpException) {
                emit(Result.Error<List<LangCards>>(e.localizedMessage ?: "An unexpected error occurred."))
            } catch (e: IOException) {
                emit(Result.Error<List<LangCards>>("Couldn't reach web service. Check your internet connection."))
            }

        }.onEach { result ->

            when (result) {
                is Result.Success -> {
                    repository.updateCardsState(
                        State<List<LangCards>>(false, result.data, null)
                    )
                }
                is Result.Error -> {
                    repository.updateCardsState(
                        State<List<LangCards>>(false, null, result.message)
                    )
                }
                is Result.Loading -> {
                    repository.updateCardsState(
                        State<List<LangCards>>(true, null, null)
                    )
                }
            }

        }.launchIn(scope)

    }

}