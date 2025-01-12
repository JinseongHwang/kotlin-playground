package me.study.section7

import kotlinx.coroutines.*

/**
 * 생성자로 생성된 Job 객체는 자동으로 실행 완료되지 않기 때문에 명시적으로 Complete 함수를 호출해줘야 한다.
 *
 * 출력 결과:
 * [main @Coroutine2#3] 코루틴 실행
 */
fun main() = runBlocking<Unit> {
    launch(CoroutineName("Coroutine1")) {
        val coroutineJ1Job = this.coroutineContext.job
        val newJob = Job(parent = coroutineJ1Job)
        launch(CoroutineName("Coroutine2") + newJob) {
            delay(100L)
            println("[${Thread.currentThread().name}] 코루틴 실행")
        }
        newJob.complete() // 명시적으로 완료 호출
    }
}