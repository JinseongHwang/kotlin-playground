package lec03

fun main() {
    val num1: Int = 3
    val num2: Long = num1.toLong()

    val num3: Int? = null
    val num4: Long = num1?.toLong() ?: 0L
}