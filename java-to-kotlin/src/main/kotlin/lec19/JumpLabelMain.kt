package lec19

fun main() {
    val numbers = listOf(1, 2, 3, 4)
    run {
        numbers.forEach {number ->
            if (number == 2) {
                println("continue")
                return@forEach
            }
            if (number == 3) {
                println("break")
                return@run
            }
            println(number)
        }
    }

    out_loop@ for (i in 1..10) {
        for (j in 1..3) {
            if (j == 2) {
                break@out_loop
            }
            println("$i $j")
        }
    }
}