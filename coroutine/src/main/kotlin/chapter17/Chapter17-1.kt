package me.study.chapter17

import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.selects.select

/**
 * Select 사용 예시
 * Select 구문에서 사용한 onSend를 활용하면,
 * 버퍼에 공간이 있는 채널을 선택해 데이터를 전송하는 용도로 사용할 수 있다.
 */
fun main() = runBlocking<Unit> {
    val startTime = System.currentTimeMillis()
    val c1 = Channel<Char>(capacity = 2)
    val c2 = Channel<Char>(capacity = 2)

    // 값 전송
    launch {
        for (c in 'A'..'Z') {
            delay(400L)
            select<Unit> {
                c1.onSend(c) { println("${currTime(startTime)} Sent $c to 1") }
                c2.onSend(c) { println("${currTime(startTime)} Sent $c to 2") }
            }
        }
    }

    // 값 받기
    launch {
        while (true) {
            delay(1000L)
            val c = select<String> {
                c1.onReceive { "$it from 1" }
                c2.onReceive { "$it from 2" }
            }
            println("${currTime(startTime)} Received $c")
        }
    }
}

fun currTime(st: Long) = "[t = ${System.currentTimeMillis() - st}]"