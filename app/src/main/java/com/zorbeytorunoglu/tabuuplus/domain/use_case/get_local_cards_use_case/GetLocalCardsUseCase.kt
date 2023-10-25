package com.zorbeytorunoglu.tabuuplus.domain.use_case.get_local_cards_use_case

import android.content.Context
import com.zorbeytorunoglu.tabuuplus.data.CardsJsonParser
import com.zorbeytorunoglu.tabuuplus.data.dto.ParsedCardsJson
import com.zorbeytorunoglu.tabuuplus.domain.model.LangCards
import java.io.File

class GetLocalCardsUseCase {

    operator fun invoke(context: Context): ParsedCardsJson? {

        val file = File(context.filesDir, "cards.json")

        if (!file.exists() || !file.isFile) return null

        val jsonText = file.readText()

        return CardsJsonParser(jsonText).parse()

    }

}