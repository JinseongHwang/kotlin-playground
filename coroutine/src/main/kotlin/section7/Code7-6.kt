package me.study.section7

import kotlinx.coroutines.*

/**
 * 부모 코루틴의 취소는 자식 코루틴에게 전파가 된다. 부모에게는 취소 전파를 하지 않는다.
 *
 * 출력 결과: x
 */
fun main() = runBlocking<Unit> {
    val parentJob = launch(Dispatchers.IO) {
        val dbResultsDeferred: List<Deferred<String>> =
            listOf("db1", "db2", "db3").map {
                async {
                    delay(1000L)
                    println("${it}으로부터 데이터를 가져오는데 성공했습니다!")
                    return@async "${it}:data"
                }
            }
        val dbResults: List<String> = dbResultsDeferred.awaitAll() // 모든 코루틴이 완료될 때까지 대기
        println(dbResults)
    }
    parentJob.cancel() // 부모 코루틴이 실행되기 전에 취소 -> 자식 코루틴들도 모두 취소된다
}