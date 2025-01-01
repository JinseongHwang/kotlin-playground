package me.study.section4.code1

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

/**
 * 만약 토큰 업데이트 완료 후 네트워크 호출을 해야 하는 상황이라면?
 *
 * 아래와 같은 코드로는 작업 순서를 보장할 수 없기 때문에 원하는 결과를 얻을 수 없다.
 */
fun main() = runBlocking<Unit> {
    val updateTokenJob = launch(Dispatchers.IO) {
        println("[${Thread.currentThread().name}] 토큰 업데이트 시작")
        delay(100L)
        println("[${Thread.currentThread().name}] 토큰 업데이트 종료")
    }

    val networkCallJob = launch(Dispatchers.IO) {
        println("[${Thread.currentThread().name}] 네트워크 호출")
    }
}