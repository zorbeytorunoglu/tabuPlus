package com.zorbeytorunoglu.tabuuplus.domain.util

import com.zorbeytorunoglu.tabuuplus.domain.model.Card
import com.zorbeytorunoglu.tabuuplus.domain.model.Lang
import com.zorbeytorunoglu.tabuuplus.domain.repository.CardRepository
import com.zorbeytorunoglu.tabuuplus.domain.repository.GameRepository
import java.util.Stack

class GameCardManager(
    private val gameRepository: GameRepository,
    private val cardRepository: CardRepository
) {

    private val cardStack: Stack<Card> = Stack()

    fun pick(): Card {
        if (cardStack.isEmpty()) {
            loadCards()
        }
        return cardStack.pop()
    }

    fun loadCards() {

        val lang = gameRepository.gameSettings.data?.cardLang ?: Lang.EN

        val langCards = cardRepository.langCardsState.value.data!!.first { it.lang == lang }

        cardStack.addAll(langCards.cards.values)

        cardStack.shuffle()

    }

}