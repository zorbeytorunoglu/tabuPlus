package com.zorbeytorunoglu.tabuuplus.common

class Holder<T> {
    var data: T? = null
    fun updateData(data: T?) {
        this.data = data
    }
}