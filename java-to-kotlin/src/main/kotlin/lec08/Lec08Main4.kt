package lec08

fun printAll(vararg strings: String) {
    for (str in strings) {
        println(str)
    }
}

fun main() {
    printAll("A", "B", "C")

    val array = arrayOf("D", "E", "F")
    printAll(*array)
}