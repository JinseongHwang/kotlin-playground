package me.study.section7

import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext

/**
 * CoroutineScope 인터페이스는 CoroutineContext를 가지고 있는 간단한 인터페이스이다.
 * CoroutineScope의 구현체를 만들려면 인터페이스를 구현하면 된다!
 *
 * 출력 결과:
 * [CustomScopeThread @coroutine#1] 코루틴 실행 완료
 */

// 커스텀 Job과 디스패처를 사용하는 CoroutineScope를 만들어보자.
class CustomCoroutineScope : CoroutineScope {
    override val coroutineContext: CoroutineContext =
        Job() + newSingleThreadContext("CustomScopeThread")
}

fun main() {
    val coroutineScope = CustomCoroutineScope()
    coroutineScope.launch {
        delay(100L)
        println("[${Thread.currentThread().name}] 코루틴 실행 완료")
    }
    Thread.sleep(1000L) // 코드 종료 방지
}