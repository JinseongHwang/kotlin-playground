package me.study.section4.code1

import kotlinx.coroutines.*

/**
 * 오래 걸리는 코루틴 생성을 cancel로 중단할 수 있다
 * 백그라운드 스레드를 점유하는 문제를 해결할 수 있다.
 */
fun main() = runBlocking<Unit> {
    val startTime = System.currentTimeMillis()
    val longJob = launch(Dispatchers.Default) {
        repeat(10) { repeatTime ->
            delay(1000L)
            println("[${Thread.currentThread().name}] [${getElapsedTime(startTime)}ms 소요] 반복 횟수 : $repeatTime")
        }
    }

    delay(2500L)
    longJob.cancel()
}
