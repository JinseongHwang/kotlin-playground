package me.study.section4.code1

import kotlinx.coroutines.*

/**
 * yield 함수를 호출한 코루틴은 자신이 사용하던 스레드를 양보한다.
 * 스레드를 양보한다는 것은 코루틴이 스레드 사용을 중단하고, 일시 중단한다는 뜻
 * 스레드를 양보한 후 곧바로 재개 요청된다.
 *
 * 단점: delay와 yield 모두 일시 중단 후 재개 과정을 거친다. 재개 시에는 CorouttineDispatcher에 의해 스레드가 할당되는 과정이 다시 발생하기 때문에 비효율적이다.
 */
fun main() = runBlocking<Unit> {
    val whileJob = launch(Dispatchers.Default) {
        while (true) {
            println("[${Thread.currentThread().name}] 작업 중")
            yield()
        }
    }
    delay(100L)
    whileJob.cancel()
}
