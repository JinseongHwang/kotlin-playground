package me.study.section3.code1

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

/**
 * limitedParallelism 확장 함수를 사용한 코드
 * 제약사항: This declaration needs opt-in. Its usage should be marked with '@kotlinx. coroutines. ExperimentalCoroutinesApi' or '@OptIn(kotlinx. coroutines. ExperimentalCoroutinesApi::class)'
 * 번역: 이 선언에는 선택 사항이 필요합니다. 사용 방법은 '@kotlinx. coroutines. ExperimentalCoroutinesApi' 또는 '@OptIn(kotlinx. coroutines. ExperimentalCoroutinesApi::class)'로 표시해야 합니다
 */
fun main() = runBlocking<Unit> {
    val imageProcessingDispatcher = Dispatchers.Default.limitedParallelism(2)

    repeat(100) {
        launch(imageProcessingDispatcher) {
            Thread.sleep(1000L)
            println("[${Thread.currentThread().name}] 이미지 처리 완료")
        }
    }
}