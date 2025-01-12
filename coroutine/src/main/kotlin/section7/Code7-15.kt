package me.study.section7

import kotlinx.coroutines.*

/**
 * Job 구조화 깨는 방법 - 1번
 * : CoroutineScope 생성 함수는 새로운 루트 Job을 가진 CoroutineContext를 생성한다.
 *
 * 따라서 main 스레드가 사용하는 runBlocking 아래에는 어떠한 작업이 없기 때문에 100ms 이전에 작업이 모두 끝난다.
 *
 * 출력 결과:
 * x
 */
fun main() = runBlocking<Unit> {
    val newScope = CoroutineScope(Dispatchers.IO) // 새로운 루트 Job 생성
    newScope.launch(CoroutineName("Coroutine1")) {
        launch(CoroutineName("Coroutine3")) {
            delay(100L)
            println("[${Thread.currentThread().name}] 코루틴 실행")
        }
        launch(CoroutineName("Coroutine4")) {
            delay(100L)
            println("[${Thread.currentThread().name}] 코루틴 실행")
        }
    }

    newScope.launch(CoroutineName("Coroutine2")) {
        launch(CoroutineName("Coroutine5")) {
            delay(100L)
            println("[${Thread.currentThread().name}] 코루틴 실행")
        }
    }
}