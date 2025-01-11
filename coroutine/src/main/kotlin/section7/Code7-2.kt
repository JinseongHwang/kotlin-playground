package me.study.section7

import kotlinx.coroutines.CoroutineName
import kotlinx.coroutines.launch
import kotlinx.coroutines.newSingleThreadContext
import kotlinx.coroutines.runBlocking

/**
 * 부모 코루틴은 실행 환경(CoroutineContext)을 자식에게 상속한다.
 * 자식 코루틴 생성 시 CoroutineContext를 지정하지 않으면 부모 코루틴의 CoroutineContext를 상속받는다.
 *
 * 출력 결과:
 * [MyThread @MyCoroutine#2] 부모 코루틴 실행
 * [MyThread @MyCoroutine#3] 자식 코루틴 실행
 */
fun main() = runBlocking<Unit> {
    val coroutineContext = // custom CoroutineContext
        newSingleThreadContext("MyThread") + // custom CoroutineDispatcher
                CoroutineName("MyCoroutine") // custom CoroutineName
    launch(coroutineContext) {
        println("[${Thread.currentThread().name}] 부모 코루틴 실행")
        launch {
            println("[${Thread.currentThread().name}] 자식 코루틴 실행")
        }
    }
}