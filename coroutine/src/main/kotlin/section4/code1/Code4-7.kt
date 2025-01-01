package me.study.section4.code1

import kotlinx.coroutines.CoroutineStart
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

/**
 * launch 코루틴이 생성되었지만 실행이 계속 지연되기 때문에 애플리케이션이 종료되지 않는다.
 */
fun main() = runBlocking<Unit> {
    val startTime = System.currentTimeMillis()
    val lazyJob = launch(start = CoroutineStart.LAZY) {
        println("[${getElapsedTime(startTime)}ms 소요] launch 코루틴 지연 실행")
    }
}
