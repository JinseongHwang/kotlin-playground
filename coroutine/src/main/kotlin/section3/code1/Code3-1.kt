package me.study.section3.code1

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.launch
import kotlinx.coroutines.newSingleThreadContext
import kotlinx.coroutines.runBlocking

/**
 * 코루틴은 특정 스레드에 붙어서 실행할 수 있는 작업 단위이다.
 * (사전) dispatch: 보내다 + er: 동작하는 사람 = 보내는 사람
 * CoroutineDispatcher는 코루틴을 보내는 사람(객체)이다.
 * 정확하게: 코루틴을 실행할 스레드를 지정하는 인터페이스이다.
 */
val singleThreadDispatcher: CoroutineDispatcher = newSingleThreadContext("singleThread")

fun main() = runBlocking<Unit> {
    launch(singleThreadDispatcher) {
        println("[${Thread.currentThread().name}] 실행!")
    }
}