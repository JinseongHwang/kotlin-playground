package me.study.section3.code1

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

/**
 * CPU 바운드 작업에 유리한 Dispatchers.Default를 사용한 코드
 */
fun main() = runBlocking<Unit> {
    launch(Dispatchers.Default) {
        launch {
            println("[${Thread.currentThread().name}] 작업 1 실행!")
        }
        launch {
            println("[${Thread.currentThread().name}] 작업 2 실행!")
        }
        launch {
            println("[${Thread.currentThread().name}] 작업 3 실행!")
        }
    }
}