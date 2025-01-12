package me.study.section7

import kotlinx.coroutines.*

/**
 * Job 생성 시 parent 파라미터 설정을 통해 부모 Job을 지정할 수 있다. 지정하지 않으면 Root Job이 된다.
 * 다만, 생성자를 통해 생성된 Job은 작업이 끝났을 때 자동으로 실행 완료 상태가 되지 않는다.
 *
 * 출력 결과:
 * [main @Coroutine2#3] 코루틴 실행
 * (실행 중...)
 */
fun main() = runBlocking<Unit> {
    launch(CoroutineName("Coroutine1")) {
        val coroutineJ1Job = this.coroutineContext.job
        val newJob = Job(parent = coroutineJ1Job)
        launch(CoroutineName("Coroutine2") + newJob) {
            delay(100L)
            println("[${Thread.currentThread().name}] 코루틴 실행")
        }
    }
}