package coroutine

import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withTimeoutOrNull

fun main() = runBlocking {
    println("Start")
    // withTimeoutOrNull 또한 join, await 처럼 동기 함수이다.
    var result = withTimeoutOrNull(50) {
        for (i in 1..10) {
            println(i)
            delay(10)
        }
        "Finish"
    }
    println(result)
}