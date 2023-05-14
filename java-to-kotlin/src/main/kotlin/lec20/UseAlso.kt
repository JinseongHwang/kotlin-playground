package lec20

fun main() {
    val list = mutableListOf("one", "two", "three")
    list.also { println("The list elements before adding new one: $it") }
        .add("four")
    println(list)
}