package me.study.section7

import kotlinx.coroutines.*

/**
 * newScope 생성 시 내부의 CoroutineContext에는 name, 신규 job, dispatcher가 포함된다.
 * newScope에서 launch로 자식 코루틴 생성 시 name을 재설정 하고 있다. 덮어써진 Context가 생성된다.
 * 이때, 자식 코루틴의 Job 또한 새롭게 생성되며 부모 코루틴으로 newScope context의 job을 가지는 것을 확인할 수 있다.
 *
 * - JobImpl : Scope 생성 시 Job 구현체
 * - StandaloneCoroutine : launch로 생성된 Job 구현체
 *
 * 출력 결과:
 * newScope의 coroutineContext: [CoroutineName(MyCoroutine), JobImpl{Active}@50943cf8, Dispatchers.IO]
 * launch 코루틴의 coroutineContext: [CoroutineName(LaunchCoroutine), CoroutineId(1), "LaunchCoroutine#1":StandaloneCoroutine{Active}@68eeddbd, Dispatchers.IO]
 * launch 코루틴의 parentJob: JobImpl{Active}@50943cf8
 */
fun main() {
    val newScope = CoroutineScope(CoroutineName("MyCoroutine") + Dispatchers.IO)
    newScope.launch(context = CoroutineName("LaunchCoroutine")) {
        println("newScope의 coroutineContext: ${newScope.coroutineContext}")
        println("launch 코루틴의 coroutineContext: ${this.coroutineContext}")
        println("launch 코루틴의 parentJob: ${this.coroutineContext.job.parent}")
    }
    Thread.sleep(1000L)
}