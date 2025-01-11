package me.study.section7

import kotlinx.coroutines.job
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

/**
 * 그렇다면 부모 코루틴의 Job과 자식 코루틴의 Job이 전혀 다를까? 아니다.
 * 부모 코루틴의 Job은 children 접근자로 자식 코루틴의 Job에 접근 가능하고, 자식 코루틴의 Job은 parent 접근자로 부모 코루틴의 Job에 접근 가능하다.
 * 이는 코루틴 구조화에 핵심적인 역할을 한다.
 *
 * Job의 계층 프로퍼티:
 * - parent: Job? -> 코루틴은 부모 코루틴이 없을 수 있고, 부모 코루틴이 있더라도 최대 하나이다.
 * - children: Sequence<Job> -> 하나의 코루틴이 여러 자식 코루틴을 가질 수 있다.
 */
fun main() = runBlocking<Unit> {
    val parentJob = this.coroutineContext.job
    println(parentJob.parent) // 최상위 코루틴은 부모가 없다 (null)

    launch {
        val childJob = this.coroutineContext.job
        println("1. 자식 코루틴의 Job이 가지고 있는 parent는 부모 코루틴의 Job인가? ${childJob.parent === parentJob}") // true
        println("2. 부모 코루틴의 Job은 자식 코루틴의 Job을 참조로 가지는가? ${parentJob.children.contains(childJob)}") // true
    }
}