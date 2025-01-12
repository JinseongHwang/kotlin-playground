package me.study.section7

import kotlinx.coroutines.*

/**
 * CoroutineScope에는 cancel 메서드가 존재한다.
 * 이 메서드를 호출하면 해당 CoroutineScope의 CoroutineContext.job 에 접근해 cancel 요청을 한다.
 *
 * 출력 결과:
 * [main @Coroutine2#3] 코루틴 실행
 */
fun main() = runBlocking<Unit> {
    launch(CoroutineName("Coroutine1")) {
        launch(CoroutineName("Coroutine3")) {
            println("[${Thread.currentThread().name}] 코루틴 실행")
        }
        launch(CoroutineName("Coroutine4")) {
            println("[${Thread.currentThread().name}] 코루틴 실행")
        }
        this.cancel() // Coroutine1의 CoroutineScope에 cancel 요청
    }

    launch(CoroutineName("Coroutine2")) {
        println("[${Thread.currentThread().name}] 코루틴 실행")
    }
}