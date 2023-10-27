package com.zorbeytorunoglu.tabuuplus.data.remote

import com.zorbeytorunoglu.tabuuplus.common.Constants
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.GET

interface TabuApi {

    @GET("${Constants.API_URL}${Constants.CARDS_FILE_NAME}")
    suspend fun getCards(): Response<ResponseBody>

}