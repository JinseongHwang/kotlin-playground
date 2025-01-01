package me.study.section4.code1

import kotlinx.coroutines.*

/**
 * launch 함수는 수신 객체로 CoroutineScope를 사용한다.
 * CoroutineScope의 isActive 확장 프로퍼티를 사용하여 코루틴이 취소되었는지 확인할 수 있다.
 */
fun main() = runBlocking<Unit> {
    val whileJob = launch(Dispatchers.Default) {
        while (this.isActive) {
            println("[${Thread.currentThread().name}] 작업 중")
        }
    }
    delay(100L)
    whileJob.cancel()
}
