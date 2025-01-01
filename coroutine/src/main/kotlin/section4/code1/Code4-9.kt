package me.study.section4.code1

import kotlinx.coroutines.*

/**
 * 오래 걸리는 코루틴 생성
 * 백그라운드 스레드 1개를 계속 점유하는 문제가 있다.
 */
fun main() = runBlocking<Unit> {
    val startTime = System.currentTimeMillis()
    val longJob = launch(Dispatchers.Default) {
        repeat(10) { repeatTime ->
            delay(1000L)
            println("[${Thread.currentThread().name}] [${getElapsedTime(startTime)}ms 소요] 반복 횟수 : $repeatTime")
        }
    }
}
