package lec08

fun repeat(
    str: String,
    num: Int = 3,
    useNewLine: Boolean = true,
) {
    for (i in 1..num) {
        if (useNewLine) {
            println(str)
        } else {
            print(str)
        }
    }
}

fun main() {
    repeat("Hello", 1, true)
    println("---")
    repeat("Hello")
}