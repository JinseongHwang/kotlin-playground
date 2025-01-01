package me.study.section4.code1

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

/**
 * 만약 토큰 업데이트 완료 후 네트워크 호출을 해야 하는 상황이라면?
 *
 * Job.join() 을 호출하면 특정 Job 이 끝날 때까지 대기할 수 있다.
 */
fun main() = runBlocking<Unit> {
    val updateTokenJob = launch(Dispatchers.IO) {
        println("[${Thread.currentThread().name}] 토큰 업데이트 시작")
        delay(100L)
        println("[${Thread.currentThread().name}] 토큰 업데이트 종료")
    }
    updateTokenJob.join() // updateTokenJob이 종료될 때까지 runBlocking 스레드를 일시 중단함

    val networkCallJob = launch(Dispatchers.IO) {
        println("[${Thread.currentThread().name}] 네트워크 호출")
    }
}