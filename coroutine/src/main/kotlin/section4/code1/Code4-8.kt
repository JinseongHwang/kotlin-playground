package me.study.section4.code1

import kotlinx.coroutines.CoroutineStart
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

/**
 * 지연 코루틴을 실행시키기 위해 job.start() 혹은 job.join()을 호출한다.
 * - start()를 호출하면 runBlocking 코루틴을 일시 중단하지 않는다.
 * - join()을 호출하면 runBlocking 코루틴을 일시 중단한다.
 */
fun main() = runBlocking<Unit> {
    val startTime = System.currentTimeMillis()
    val lazyJob = launch(start = CoroutineStart.LAZY) {
        println("[${getElapsedTime(startTime)}ms 소요] launch 코루틴 지연 실행")
    }

    delay(1000L)
    lazyJob.start() // 코루틴 실행
//    lazyJob.join() // join으로 실행해도 동작한다.
}
