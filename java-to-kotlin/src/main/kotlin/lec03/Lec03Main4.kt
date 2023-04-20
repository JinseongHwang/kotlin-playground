package lec03

fun main() {
    val str = """
        ABC
        123
        4567890
    """.trimIndent()
    println(str.javaClass)
    println(str.length)
    println(str.replace("\n", "#"))
}