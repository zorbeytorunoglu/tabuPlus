package com.zorbeytorunoglu.tabuuplus.data.dto

import com.zorbeytorunoglu.tabuuplus.domain.model.LangCards

data class CardsData(
    val version: Int = 1,
    val langCardsList: List<LangCards>?
)