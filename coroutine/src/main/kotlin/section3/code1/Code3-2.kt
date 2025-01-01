package me.study.section3.code1

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.launch
import kotlinx.coroutines.newFixedThreadPoolContext
import kotlinx.coroutines.runBlocking

/**
 * 제약사항
 * Message: This is a delicate API and its use requires care. Make sure you fully read and understand documentation of the declaration that is marked as a delicate API.
 * 번역: 이 API는 민감하게 사용되어야 하며 사용에 주의가 필요합니다. 민감한 API로 표시된 선언의 문서를 완전히 읽고 이해해야 합니다.
 *
 * 이유: 미리 스레드를 만들어서 메모리에 올려두게 되는데, 이 스레드는 다른 개발자가 용도를 이해하기 힘들며 사용하기도 어렵다.
 * 해결책: 이런 경우에는 코루틴에서 제공되는 디스패처(Dispatcher.IO, Dispatcher.Default, Dispatcher.Main)를 사용하는 것이 좋다.
 */
val multiThreadDispatcher: CoroutineDispatcher = newFixedThreadPoolContext(2, "multiThread")

fun main() = runBlocking<Unit> {
    launch(multiThreadDispatcher) {
        println("[${Thread.currentThread().name}] 실행!")
    }

    launch(multiThreadDispatcher) {
        println("[${Thread.currentThread().name}] 실행!")
    }
}