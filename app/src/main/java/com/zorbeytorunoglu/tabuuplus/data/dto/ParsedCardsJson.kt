package com.zorbeytorunoglu.tabuuplus.data.dto

import com.zorbeytorunoglu.tabuuplus.domain.model.LangCards

data class ParsedCardsJson(
    val version: Int = 1,
    val data: List<LangCards>?
)