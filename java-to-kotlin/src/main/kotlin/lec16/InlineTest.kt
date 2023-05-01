package lec16

inline fun Int.add3(other: Int): Int {
    return this + other
}

fun main() {
    println(3.add3(4))
}

