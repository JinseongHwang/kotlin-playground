package me.study.chapter16

import kotlinx.coroutines.*
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.ReceiveChannel
import kotlinx.coroutines.channels.SendChannel
import kotlinx.coroutines.channels.produce

/**
 * Fan-in 예시
 */
fun main() = runBlocking<Unit> {
    val fooChannel = Channel<String>()
    val barChannel = Channel<String>()

    launch { sendString(fooChannel, "foo", 200L) }
    launch { sendString(barChannel, "BAR!", 500L) }

    val fanInChannel = fanIn(fooChannel, barChannel)
    repeat(20) {
        println(fanInChannel.receive())
    }

    coroutineContext.cancelChildren()
}

suspend fun sendString(channel: SendChannel<String>, text: String, time: Long) {
    while (true) {
        delay(time)
        channel.send(text)
    }
}

fun <T> CoroutineScope.fanIn(
    vararg channels: ReceiveChannel<T>
): ReceiveChannel<T> = produce {
    for (channel in channels) {
        launch {
            for (elem in channel) {
                send(elem)
            }
        }
    }
}
