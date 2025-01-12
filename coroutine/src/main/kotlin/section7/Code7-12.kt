package me.study.section7

import kotlinx.coroutines.*

/**
 * CoroutineScope를 새로 생성하면, 특정 코루틴을 기존 CoroutineScope 범위에서 벗어나도록 만들 수 있다.
 * Coroutine1,2,3 은 모두 runBlocking 코루틴의 자식, 자손 관계이지만, Coroutine4는 CoroutineScope(Dispatchers.IO) 코루틴을 부모로 가지는 별도의 코루틴이다.
 *
 * 이렇게 의도적으로 구조화를 깨는 것은 코루틴 생명주기를 관리하게 어렵게 만들기 때문에 권장되는 방식은 아니다.
 *
 * 출력 결과:
 * [main @Coroutine2#3] 코루틴 실행
 * [DefaultDispatcher-worker-1 @Coroutine4#5] 코루틴 실행
 * [main @Coroutine3#4] 코루틴 실행
 */
fun main() = runBlocking<Unit> {
    launch(CoroutineName("Coroutine1")) {
        launch(CoroutineName("Coroutine3")) {
            println("[${Thread.currentThread().name}] 코루틴 실행")
        }
        CoroutineScope(Dispatchers.IO).launch(CoroutineName("Coroutine4")) {
            println("[${Thread.currentThread().name}] 코루틴 실행")
        }
    }

    launch(CoroutineName("Coroutine2")) {
        println("[${Thread.currentThread().name}] 코루틴 실행")
    }
}