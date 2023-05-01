package lec16

fun String.lastChar(): Char {
    return this[this.length - 1]
}

val String.lastChar: Char
    get() = this[this.length - 1]

fun main() {
    val s = "ABC"
    val lastChar = s.lastChar()
    println(lastChar)
}