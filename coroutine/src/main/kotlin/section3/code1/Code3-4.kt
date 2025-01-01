package me.study.section3.code1

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

/**
 * 코루틴은 구조화를 지원하기 때문에 부모,자식 관계로 만들 수 있다.
 * 부모 코루틴에서 Dispatchers.IO를 사용하도록 지정하면 자식 코루틴에서는 별도의 설정이 없다면 부모의 디스패처를 그대로 사용한다.
 */
fun main() = runBlocking<Unit> {
    launch(Dispatchers.IO) {
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