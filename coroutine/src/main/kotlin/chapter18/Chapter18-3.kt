package me.study.chapter18

import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.flow

/**
 * 채널(hot data stream)과 플로우(cold data stream) 비교하기
 * 아래는 플로우 예시
 * 플로우는 연산 그 자체이기 때문에 플로우에 대한 종단 연산이 여러번 가능하다.
 */
suspend fun main() = coroutineScope {
    val flow = makeFlow()

    delay(1000)
    println("Calling flow...")
    flow.collect { value -> println(value) }
    println("Consuming again...")
    flow.collect { value -> println(value) }
}

fun makeFlow() = flow {
    println("Flow started")
    for (i in 1..3) {
        delay(1000)
        emit(i)
    }
}
