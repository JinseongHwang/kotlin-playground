package me.study.section7

import kotlinx.coroutines.job
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

/**
 * launch or async를 포함한 모든 코루틴 빌더 함수는 호출 시마다 코루틴 추상체인 Job 객체를 새롭게 생성한다.
 * 코루틴 제어에 Job 객체가 필요하기 때문에, Job 객체를 부모 코루틴으로부터 상속 받지 않는다.
 * 즉, 코루틴 빌더를 통해 생성된 코루틴들은 서로 다른 Job을 가진다.
 *
 * CoroutineContext를 통해서 Job 객체를 얻어올 수 있다.
 *
 * 출력 결과:
 * runBlockingJob is not the same as launchJob
 */
fun main() = runBlocking<Unit> {
    val runBlockingJob = this.coroutineContext.job // == this.coroutineContext[Job]
    launch {
        val launchJob = this.coroutineContext.job
        if (runBlockingJob == launchJob) {
            println("runBlockingJob is the same as launchJob")
        } else {
            println("runBlockingJob is not the same as launchJob")
        }
    }
}