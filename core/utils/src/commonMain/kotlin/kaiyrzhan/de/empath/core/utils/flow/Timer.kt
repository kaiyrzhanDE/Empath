package kaiyrzhan.de.empath.core.utils.flow

import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

public fun timerFlow(duration: Int): Flow<Int> = flow {
    for (remaining in duration downTo 0) {
        emit(remaining)
        delay(1000L)
    }
}