package me.study.section7

import kotlinx.coroutines.CoroutineName
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import me.study.section4.code1.printJobState

/**
 * 부모 코루틴은 모든 자식 코루틴이 실행 완료돼야 완료될 수 있다.
 * 코루틴의 구조화는 큰 작업을 연관된 여러 작업으로 나누는 방식으로 일어나는데, 작은 작업이 완료돼야 큰 작업이 완료될 수 있기 때문이다.
 *
 * 출력 결과:
 * [지난 시간: 5ms] 부모 코루틴이 실행하는 마지막 코드
 * [지난 시간: 1016ms] 자식 코루틴 실행 완료
 * [지난 시간: 1017ms] 부모 코루틴 실행 완료
 *
 * ---
 *
 * 부모 코루틴의 마지막 코드가 실행되고, 실행 완료 되기까지의 Job 상태는 무엇일까?
 * 바로 실행완료중(Completing) 상태이다. 모든 자식 코루틴이 실행완료(Completed) 되어야 부모 코루틴도 비로소 실행완료 상태로 바뀐다.
 *
 * 부모 코루틴의 상태를 찍어보면:
 * "Parent#2":StandaloneCoroutine{Completing}@7a92922
 * Job State:
 * 	 isActive >> true
 * 	 isCancelled >> false
 * 	 isCompleted >> false
 * Job State 값들은 실행중 == 실행완료중 인 것을 알 수 있다.
 */
fun main() = runBlocking<Unit> {
    val startTime = System.currentTimeMillis()
    val parentJob = launch(CoroutineName("Parent")) {
        launch(CoroutineName("Child")) {
            delay(1000L)
            println("[${getElapsedTime(startTime)}] 자식 코루틴 실행 완료") // 2. 부모 Completing, 자식 Active
        }
        println("[${getElapsedTime(startTime)}] 부모 코루틴이 실행하는 마지막 코드") // 1. 부모 Active, 자식 Active
    }
    parentJob.invokeOnCompletion { // invokeOnCompletion은 코루틴이 완료될 때 호출되는 콜백 함수
        println("[${getElapsedTime(startTime)}] 부모 코루틴 실행 완료") // 3. 코루틴 없음
    }

    // 부모 코루틴의 실행완료중 상태 디버깅
    delay(500L)
    println(parentJob)
    printJobState(parentJob)
}

private fun getElapsedTime(startTime: Long) =
    "지난 시간: ${System.currentTimeMillis() - startTime}ms"