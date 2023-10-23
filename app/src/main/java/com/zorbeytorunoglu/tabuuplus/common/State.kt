package com.zorbeytorunoglu.tabuuplus.common

data class State<T>(
    val isLoading: Boolean = false,
    val data: T? = null,
    val message: String? = null
) {
}