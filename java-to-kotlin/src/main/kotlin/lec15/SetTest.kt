package lec15

fun main() {
    val numbers = setOf(100, 200)
    val mutableNumbers = mutableSetOf(300, 400) // LinkedHashSet

    for (number in numbers) {
        println(number)
    }

    for ((idx, number) in numbers.withIndex()) {
        println("$idx : $number")
    }
}