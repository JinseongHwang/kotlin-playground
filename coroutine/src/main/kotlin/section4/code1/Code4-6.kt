package me.study.section4.code1

import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

fun main() = runBlocking<Unit> {
    val startTime = System.currentTimeMillis()
    val immediateJob = launch {
        println("[${getElapsedTime(startTime)}ms 소요] launch 코루틴 즉시 실행")
    }
}

fun getElapsedTime(startTime: Long): Long {
    return System.currentTimeMillis() - startTime
}