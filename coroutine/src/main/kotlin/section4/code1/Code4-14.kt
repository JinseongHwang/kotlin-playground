package me.study.section4.code1

import kotlinx.coroutines.*

/**
 * 아래와 같이 작성하면 무한 반복된다.
 * whileJob.cancel을 "취소 확인" 으로 바꿔줄 시간이 없기 때문 (중단이 없기 때문)
 */
fun main() = runBlocking<Unit> {
    val whileJob = launch(Dispatchers.Default) {
        while (true) {
            println("작업 중")
        }
    }
    delay(100L)
    whileJob.cancel()
}
