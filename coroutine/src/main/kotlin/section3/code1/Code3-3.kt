package me.study.section3.code1

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

/**
 * 코루틴 생성 시점에 사용할 디스패처를 지정해줄 수 있다.
 * Dispatchers.IO: I/O 작업을 위한 스레드
 */
fun main() = runBlocking<Unit> {
    launch(Dispatchers.IO) {
        println("[${Thread.currentThread().name}] 작업 1 실행!")
    }
    launch(Dispatchers.IO) {
        println("[${Thread.currentThread().name}] 작업 2 실행!")
    }
    launch(Dispatchers.IO) {
        println("[${Thread.currentThread().name}] 작업 3 실행!")
    }
}