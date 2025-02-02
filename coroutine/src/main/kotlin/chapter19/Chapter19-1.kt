package me.study.chapter19

import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.newSingleThreadContext
import kotlinx.coroutines.withContext

suspend fun main() {
    withContext(newSingleThreadContext("main")) {
        launch {
            repeat(3) {
                delay(100)
                println("Processing on coroutine")
            }
        }

        val list = getSequence()
        list.forEach { println(it) }
    }
}

fun getSequence() = sequence {
    repeat(3) {
        Thread.sleep(1000)
        // 여기에 delay(1000)이 있어도 결과는 같다.
        yield("User$it")
    }
}
