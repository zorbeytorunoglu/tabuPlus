package com.zorbeytorunoglu.tabuuplus.data.repository

import com.zorbeytorunoglu.tabuuplus.common.Holder
import com.zorbeytorunoglu.tabuuplus.domain.model.GameSettings
import com.zorbeytorunoglu.tabuuplus.domain.repository.GameRepository

class GameRepositoryImpl: GameRepository {

    override val gameSettings: Holder<GameSettings> = Holder()

}