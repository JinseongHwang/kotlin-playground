package me.study.section4.code1

import kotlinx.coroutines.*

/**
 * 코루틴이 취소된 후 실행돼야 하는 코루틴이 있다면, 코루틴을 취소할 때 cancelAndJoin 함수를 사용하면 된다
 * cancelAndJoin = cancel + join ==> 취소 요청한 후 취소가 완료될 때까지 호출 코루틴(아래에서는 runBlocking)을 일시 중단한다
 *
 * 아래 코드 실행 결과:
 * ```
 * longJob 코루틴이 동작
 * longJob 코루틴 취소 후 실행돼야 하는 동작
 * ```
 *
 * 여전히 "longJob 코루틴이 동작" 이 출력되는 문제가 있다...
 */
fun main() = runBlocking<Unit> {
    val longJob = launch(Dispatchers.Default) {
        Thread.sleep(1000L)
        println("longJob 코루틴이 동작")
    }
    longJob.cancelAndJoin()
    executeAfterJobCancelled()
}
