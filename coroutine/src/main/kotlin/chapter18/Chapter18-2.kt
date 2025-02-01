package me.study.chapter18

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.produce
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay

/**
 * 채널(hot data stream)과 플로우(cold data stream) 비교하기
 * 아래는 채널 예시
 * 채널은 한번 iterate 하면 다시 consuming 불가능하다.
 */
suspend fun main() = coroutineScope {
    val channel = makeChannel()

    delay(1000L)
    println("Calling channel...")
    for (value in channel) {
        println(value)
    }
    println("Consuming again...")
    for (value in channel) {
        println(value)
    }
}

fun CoroutineScope.makeChannel() = produce(capacity = Channel.RENDEZVOUS) {
    println("Channel started")
    for (i in 1..3) {
        delay(1000L)
        send(i)
    }
}
