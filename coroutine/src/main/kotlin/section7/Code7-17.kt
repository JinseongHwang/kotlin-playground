package me.study.section7

import kotlinx.coroutines.*

/**
 * Job 객체를 생성해서 일부 코루틴만 취소되지 않도록 만들 수 있다.
 * Coroutine5 에만 새로운 Root Job을 할당해서 기존 Job의 취소와 무관하게 만들었다. 즉, 구조화를 깼다.
 *
 * 출력 결과:
 * [main @Coroutine5#6] 코루틴 실행
 */
fun main() = runBlocking<Unit> {
    val newRootJob = Job() // 새로운 루트 Job 생성
    launch(CoroutineName("Coroutine1") + newRootJob) {
        launch(CoroutineName("Coroutine3")) {
            delay(100L)
            println("[${Thread.currentThread().name}] 코루틴 실행")
        }
        launch(CoroutineName("Coroutine4")) {
            delay(100L)
            println("[${Thread.currentThread().name}] 코루틴 실행")
        }
    }

    launch(CoroutineName("Coroutine2") + newRootJob) {
        // 새로운 Job 객체가 할당된 Context를 할당해서, Root Job을 새롭게 생성된 Job을 사용하도록 한다.
        // 따라서 기존 Root Job인 newRootJob의 취소와 무관하게 동작한다.
        launch(CoroutineName("Coroutine5") + Job()) {
            delay(100L)
            println("[${Thread.currentThread().name}] 코루틴 실행")
        }
    }

    delay(50L) // 모든 코루틴이 생성될 때까지 대기
    newRootJob.cancel() // newRootJob 과 그 자식 코루틴 취소
    delay(1000L) // 모든 코루틴이 실행 완료 될 때까지 대기
}