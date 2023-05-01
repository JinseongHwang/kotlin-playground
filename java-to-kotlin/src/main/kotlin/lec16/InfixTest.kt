package lec16

fun Int.add(other: Int): Int {
    return this + other
}

infix fun Int.add2(other: Int): Int {
    return this + other
}

fun main() {
    println(3.add(4))
    println(3.add2(4))
    println(3 add2 4)
}