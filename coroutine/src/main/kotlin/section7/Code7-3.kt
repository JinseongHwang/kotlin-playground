package me.study.section7

import kotlinx.coroutines.CoroutineName
import kotlinx.coroutines.launch
import kotlinx.coroutines.newSingleThreadContext
import kotlinx.coroutines.runBlocking

/**
 * 자식 코루틴 생성 시 다른 Context를 넣어주면 상속받은 부모 코루틴의 Context에 덮어쓰기 된다.
 * 아래 코드의 경우에는 CoroutineDispatcher는 부모를 따라가고, CoroutineName만 자식 코루틴의 이름으로 변경된다.
 *
 * 출력 결과:
 * [MyThread @ParentCoroutine#2] 부모 코루틴 실행
 * [MyThread @ChildCoroutine#3] 자식 코루틴 실행
 */
fun main() = runBlocking<Unit> {
    val coroutineContext = // custom CoroutineContext
        newSingleThreadContext("MyThread") + // custom CoroutineDispatcher
                CoroutineName("ParentCoroutine") // custom CoroutineName
    launch(coroutineContext) {
        println("[${Thread.currentThread().name}] 부모 코루틴 실행")
        launch(CoroutineName("ChildCoroutine")) {
            println("[${Thread.currentThread().name}] 자식 코루틴 실행")
        }
    }
}