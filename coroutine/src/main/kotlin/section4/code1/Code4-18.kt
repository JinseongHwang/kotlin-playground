package me.study.section4.code1

import kotlinx.coroutines.*

/**
 * 다양한 상태의 코루틴 만들어보기
 */
fun main() = runBlocking<Unit> {
    val job = launch {
        delay(1000L)
    }
    println(job) // Active
}
