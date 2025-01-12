package me.study.section7

import kotlinx.coroutines.*

/**
 * 조금 더 쉽게 CoroutineScope를 생성하는 방법은 아래와 같다.
 * CoroutineScope의 생성자를 살펴보면 입력된 Dispatcher와 Job을 사용하여 CoroutineContext를 생성해서 CoroutineScope를 만들어준다.
 * IO Dispatcher를 전달하면 DefaultDispatcher-worker 스레드가 사용되는 것을 확인할 수 있다.
 *
 * 출력 결과:
 * [DefaultDispatcher-worker-1 @coroutine#1] 코루틴 실행 완료
 */
fun main() {
    val coroutineScope = CoroutineScope(Dispatchers.IO)
    coroutineScope.launch {
        delay(100L)
        println("[${Thread.currentThread().name}] 코루틴 실행 완료")
    }
    Thread.sleep(1000L) // 코드 종료 방지
}