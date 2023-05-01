package lec15

fun main() {
    val numbers = listOf(100, 200) // Int 라고 적지 않아도 추론 가능하기 때문에 생략함.
    val emptyList = emptyList<Int>()
    val mutableNumbers = mutableListOf(300, 400)
    mutableNumbers.add(500) // mutable 이기 때문에 add 가능

    println(numbers[0])
    for (number in numbers) {
        println(number)
    }

    for ((idx, value) in numbers.withIndex()) {
        println("$idx : $value")
    }
}