package me.study.section4.code1

import kotlinx.coroutines.*

/**
 * delay 함수로 특정 시간만큼 코루틴을 일시 중단 할 수 있다.
 * 일시 중단 시점에 코루틴의 취소가 확인되어 취소된다.
 *
 * 단점: while 이 반복될 때마다 1ms 씩 중단이 발생한다. 성능 저하를 유발한다.
 */
fun main() = runBlocking<Unit> {
    val whileJob = launch(Dispatchers.Default) {
        while (true) {
            println("작업 중")
            delay(1L)
        }
    }
    delay(100L)
    whileJob.cancel()
}
