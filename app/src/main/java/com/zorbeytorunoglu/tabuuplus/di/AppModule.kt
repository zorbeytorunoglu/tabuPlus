package com.zorbeytorunoglu.tabuuplus.di

import com.zorbeytorunoglu.tabuuplus.common.Constants
import com.zorbeytorunoglu.tabuuplus.data.remote.TabuApi
import com.zorbeytorunoglu.tabuuplus.data.repository.CardRepositoryImpl
import com.zorbeytorunoglu.tabuuplus.data.repository.GameRepositoryImpl
import com.zorbeytorunoglu.tabuuplus.domain.repository.CardRepository
import com.zorbeytorunoglu.tabuuplus.domain.repository.GameRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    @Provides
    @Singleton
    fun provideCardRepository(api: TabuApi): CardRepository = CardRepositoryImpl(api)

    @Provides
    @Singleton
    fun provideTabuApi(): TabuApi {
        return Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(TabuApi::class.java)
    }

    @Provides
    @Singleton
    fun provideGameRepository(): GameRepository = GameRepositoryImpl()

}