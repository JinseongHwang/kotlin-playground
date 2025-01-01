package me.study.section4.code1

import kotlinx.coroutines.*

/**
 * Job 객체 상태 출력해보기
 *
 * New 상태일 때는 3가지 상태 모두 false
 */
fun main() = runBlocking<Unit> {
    // active
    val activeJob = launch(start = CoroutineStart.LAZY) {
        delay(1000L)
    }
    println(activeJob) // Active
    printJobState(activeJob)

    // new
    val newJob = launch(Dispatchers.IO) {
        delay(1000L)
    }
    println(newJob) // New
    printJobState(newJob)

    // completed
    val completedJob = launch {
        delay(1000L)
    }
    completedJob.join()
    println(completedJob) // Completed
    printJobState(completedJob)

    // cancelling
    val cancellingJob = launch(Dispatchers.IO) {
        Thread.sleep(1000L)
        yield()
    }
    cancellingJob.cancel()
    println(cancellingJob) // Cancelling
    printJobState(cancellingJob)

    // cancelled
    val cancelledJob = launch(Dispatchers.IO) {
        while (true) {
            yield()
        }
    }
    cancelledJob.cancelAndJoin()
    println(cancelledJob) // Cancelled
    printJobState(cancelledJob)
}

fun printJobState(job: Job) {
    println(
        "Job State:\n" +
        "\t isActive >> ${job.isActive}\n" +
        "\t isCancelled >> ${job.isCancelled}\n" +
        "\t isCompleted >> ${job.isCompleted}\n"
    )
}