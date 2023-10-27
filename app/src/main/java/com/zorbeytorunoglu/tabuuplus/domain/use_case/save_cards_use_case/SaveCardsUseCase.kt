package com.zorbeytorunoglu.tabuuplus.domain.use_case.save_cards_use_case

import android.content.Context
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import java.io.File
import javax.inject.Inject

class SaveCardsUseCase @Inject constructor() {
    operator fun invoke(scope: CoroutineScope, context: Context, json: String) {
        scope.launch {
            File(context.filesDir, "cards.json").writeText(json)
        }
    }

}