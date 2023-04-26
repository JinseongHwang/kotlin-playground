package lec06

fun main() {
    val numbers = listOf(1, 2, 3)
    for (number in numbers) {
        print(number)
    }
    println()

    for (i in 1..3) {
        print(i)
    }
    println()

    for (i in 3 downTo 1) {
        print(i)
    }
    println()

    for (i in 1..5 step 2) {
        print(i)
    }
    println()

    for (i in 10 downTo 1 step -3) {
        print(i)
    }
}