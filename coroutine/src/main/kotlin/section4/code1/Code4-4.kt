package me.study.section4.code1

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

/**
 * 실행 흐름
 *
 * 1. converImageJob1 코루틴 생성 및 실행
 * 2. converImageJob2 코루틴 생성 및 실행
 * 3. converImageJob1이 끝날 때까지 runBlocking 코루틴 일시 중단
 * 4. converImageJob1이 끝나면 runBlocking 코루틴 재개
 * 5. converImageJob2이 끝날 때까지 runBlocking 코루틴 일시 중단
 * 6. converImageJob2이 끝나면 runBlocking 코루틴 재개
 * 7. uploadImageJob 코루틴 생성 및 실행
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

    converImageJob1.join()
    converImageJob2.join()

    val uploadImageJob: Job = launch(Dispatchers.IO) {
        println("[${Thread.currentThread().name}] 이미지 1,2 업로드 완료")
    }
}