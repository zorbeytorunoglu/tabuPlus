package com.zorbeytorunoglu.tabuuplus.domain.use_case.get_local_cards_flow_use_case

import android.content.Context
import com.zorbeytorunoglu.tabuuplus.common.Result
import com.zorbeytorunoglu.tabuuplus.data.CardsJsonParser
import com.zorbeytorunoglu.tabuuplus.domain.repository.CardRepository
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.File
import java.io.IOException
import javax.inject.Inject

class GetLocalCardsFlowUseCase @Inject constructor(
    private val repository: CardRepository
) {

    operator fun invoke(context: Context) = flow {

        try {
            emit(Result.Loading())
            val file = File(context.filesDir, "cards.json")
            if (!file.exists() || !file.isFile) {
                emit(Result.Error("File does not exists."))
            } else {
                val data = CardsJsonParser(file.readText()).parse()
                if (data.langCardsList.isNullOrEmpty()) {
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
    }

}