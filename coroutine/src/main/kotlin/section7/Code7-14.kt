package me.study.section7

import kotlinx.coroutines.*

/**
 * CoroutineScope의 isActive 프로퍼티를 사용하면 코루틴이 활성화 상태인지 확인할 수 있다.
 *
 * 출력 결과:
 * Still active
 * ... 100ms간 지속 후 종료
 */
fun main() = runBlocking<Unit> {
    val whileJob = launch(Dispatchers.IO) {
        while (this.isActive) {
            println("Still active")
        }
    }
    delay(100L)
    whileJob.cancel()
}