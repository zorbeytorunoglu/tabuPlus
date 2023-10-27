package com.zorbeytorunoglu.tabuuplus.data

import android.util.Log
import com.zorbeytorunoglu.tabuuplus.data.dto.CardsData
import com.zorbeytorunoglu.tabuuplus.domain.model.Card
import com.zorbeytorunoglu.tabuuplus.domain.model.Lang
import com.zorbeytorunoglu.tabuuplus.domain.model.LangCards
import org.json.JSONObject

class CardsJsonParser(jsonResponse: String) {

    private val fullJson = JSONObject(jsonResponse)

    private fun getVersion(): Int {
        return fullJson.getInt("version")
    }

    fun parse(): CardsData = CardsData(getVersion(), listOfNotNull(
        getCardMapFromLangKey(fullJson, "en"),
        getCardMapFromLangKey(fullJson, "fr"),
        getCardMapFromLangKey(fullJson, "tr")
    ))

    private fun getCardMapFromLangKey(fullJson: JSONObject, keyString: String): LangCards? {

        val lang = runCatching { Lang.valueOf(keyString.uppercase()) }.onFailure {
            Log.e("TabuPlus", "Lang could not be loaded because of null enum value. $keyString")
        }.getOrNull() ?: return null

        val keyJson = fullJson.getJSONObject(keyString)

        val mutableWordMap = mutableMapOf<Int, Card>()

        for ((index, id) in keyJson.keys().withIndex()) {

            val idJson = keyJson.getJSONObject(id)
            val mainWord = idJson.getString("word")

            val relatedWordsArray = idJson.getJSONArray("related_words")

            val relatedWordsList = arrayListOf<String>()

            for (i in 0 until relatedWordsArray.length()) {
                relatedWordsList.add(relatedWordsArray.getString(i))
            }

            mutableWordMap[index] = Card(mainWord, relatedWordsList.toList())

        }

        return LangCards(lang, mutableWordMap.toMap())

    }

}