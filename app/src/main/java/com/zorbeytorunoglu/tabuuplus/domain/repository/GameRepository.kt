package com.zorbeytorunoglu.tabuuplus.domain.repository

import com.zorbeytorunoglu.tabuuplus.common.Holder
import com.zorbeytorunoglu.tabuuplus.domain.model.GameData
import com.zorbeytorunoglu.tabuuplus.domain.model.GameSettings

interface GameRepository {

    val gameSettings: Holder<GameSettings>

    val gameData: Holder<GameData>

}