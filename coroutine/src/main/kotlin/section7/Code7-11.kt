package me.study.section7

import kotlinx.coroutines.CoroutineName
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

/**
 * CoroutineScope 객체는 특정 범위의 코루틴을 제어하는 역할을 한다.
 * 범위는 CoroutineScope의 Job에 해당하는 코루틴과 자식, 자손 되는 모든 코루틴이다.
 *
 * 출력 결과:
 * [main @Coroutine2#3] 코루틴 실행
 * [main @Coroutine3#4] 코루틴 실행
 * [main @Coroutine4#5] 코루틴 실행
 */
fun main() = runBlocking<Unit> {
    launch(CoroutineName("Coroutine1")) {
        launch(CoroutineName("Coroutine3")) {
            println("[${Thread.currentThread().name}] 코루틴 실행")
        }
        launch(CoroutineName("Coroutine4")) {
            println("[${Thread.currentThread().name}] 코루틴 실행")
        }
    }
    launch(CoroutineName("Coroutine2")) {
        println("[${Thread.currentThread().name}] 코루틴 실행")
    }
}