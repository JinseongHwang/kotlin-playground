package coroutine

import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

fun main() {

    val scope = GlobalScope
    scope.launch {
        for (i in 1..5) {
            println(i)
        }
    }
}