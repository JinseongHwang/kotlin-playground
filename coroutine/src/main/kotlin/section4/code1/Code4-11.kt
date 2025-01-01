package me.study.section4.code1

import kotlinx.coroutines.*

/**
 * cancel 함수는 코루틴을 곧바로 취소하지 않고, 취소 확인용 플래그를 '취소 요청됨'으로 바꾸는 역할만 한다.
 * 이후 미래에 취소가 확인되는 시점에 코루틴이 취소된다.
 *
 * cancel 함수를 사용하는 것은 순차성 관점에서 중요한 문제를 가진다.
 *
 * 아래 코드 실행 결과:
 * ```
 * longJob 코루틴 취소 후 실행돼야 하는 동작
 * longJob 코루틴이 동작
 * ```
 */
fun main() = runBlocking<Unit> {
    val longJob = launch(Dispatchers.Default) {
        Thread.sleep(1000L)
        println("longJob 코루틴이 동작")
    }
    longJob.cancel()
    executeAfterJobCancelled()
}

fun executeAfterJobCancelled() {
    println("longJob 코루틴 취소 후 실행돼야 하는 동작")
}
