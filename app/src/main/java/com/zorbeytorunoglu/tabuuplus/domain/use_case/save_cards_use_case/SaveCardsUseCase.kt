package com.zorbeytorunoglu.tabuuplus.domain.use_case.save_cards_use_case

import android.content.Context
import java.io.File

class SaveCardsUseCase {
    operator fun invoke(context: Context, json: String) {
        File(context.filesDir, "cards.json").writeText(json)
    }

}