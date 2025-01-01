package me.study.section4.code1

import kotlinx.coroutines.*

/**
 * delay로 중단점을 넣어주면 그 때 "취소 요청"이 "취소 확인"처리로 바뀐다.
 *
 * 아래 코드 실행 결과:
 * ```
 * longJob 코루틴 취소 후 실행돼야 하는 동작
 * ```
 */
fun main() = runBlocking<Unit> {
    val longJob = launch(Dispatchers.Default) {
        delay(1000L)
        println("longJob 코루틴이 동작")
    }
    longJob.cancelAndJoin()
    executeAfterJobCancelled()
}
