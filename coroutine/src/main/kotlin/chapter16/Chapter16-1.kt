package me.study.chapter16

import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

suspend fun main() = coroutineScope {
    val channel = Channel<Int>( // Channel은 인터페이스이다. Channel은 클래스 생성자인 척 하는 메서드이다.
        capacity = 2,
        onBufferOverflow = BufferOverflow.DROP_OLDEST
    )

    launch {
        repeat(5) {index ->
            channel.send(index * 2)
            delay(100L)
            println("Sent")
        }
        channel.close()
    }

    delay(1000L)
    for (element in channel) {
        println(element)
        delay(1000L)
    }
}