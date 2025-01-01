package me.study.section4.code1

import kotlinx.coroutines.*

/**
 * joinAll을 사용하면 여러 코루틴을 한번에 기다릴 수 있다.
 */
fun main() = runBlocking<Unit> {
    val converImageJob1: Job = launch(Dispatchers.Default) {
        Thread.sleep(1000L) // 이미지 변환 작업 실행
        println("[${Thread.currentThread().name}] 이미지 1 변환 완료")
    }
    val converImageJob2: Job = launch(Dispatchers.Default) {
        Thread.sleep(1000L) // 이미지 변환 작업 실행
        println("[${Thread.currentThread().name}] 이미지 2 변환 완료")
    }

    joinAll(converImageJob1, converImageJob2)

    // 컬렉션에 대한 확장 함수도 구현되어 있기 때문에 아래와 같이 작성하는 것도 가능
    // listOf(converImageJob1, converImageJob2).joinAll()

    val uploadImageJob: Job = launch(Dispatchers.IO) {
        println("[${Thread.currentThread().name}] 이미지 1,2 업로드 완료")
    }
}