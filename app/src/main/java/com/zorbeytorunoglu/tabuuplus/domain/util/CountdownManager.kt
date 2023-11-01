package com.zorbeytorunoglu.tabuuplus.domain.util

import android.util.Log
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.util.concurrent.atomic.AtomicBoolean

class CountdownManager(
    private val initialTime: Int,
    private val scope: CoroutineScope,
    private var onFinish: (() -> Unit)? = null
) {

    private val _remainingTimeFlow = MutableStateFlow(initialTime)
    val remainingTimeFlow: StateFlow<Int> = _remainingTimeFlow

    private val isPaused = AtomicBoolean(false)

    private var countdownJob: Job? = null

    fun start() {
        _remainingTimeFlow.value = initialTime
        countdownJob = scope.launch {
            while (_remainingTimeFlow.value >= 0) {
                if (!isPaused.get()) {
                    if (_remainingTimeFlow.value == 0) {
                        stop()
                        onFinish?.invoke()
                        return@launch
                    }
                    _remainingTimeFlow.value = _remainingTimeFlow.value - 1
                }
                delay(1000)
            }
        }
    }

    fun isPaused(): Boolean = isPaused.get()

    fun pause() {
        isPaused.set(true)
    }

    fun resume() {
        isPaused.set(false)
    }

    fun stop() = countdownJob?.cancel()

    fun setOnFinish(unit: () -> Unit) {
        onFinish = unit
    }

}