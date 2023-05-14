package lec20

fun main() {
    val strings = listOf("APPLE", "CAR", "HI")
    strings.map { it.length }
        .filter { it >= 3 }
        .let(::println)

    val str: String? = "Hello"
    val length = str?.let {
        println(it.uppercase())
        it.length
    }
    println(length)

    val numbers = listOf("one", "two", "three", "four")
    val modifiedFirstItem = numbers.first()
        .let { firstItem ->
            if (firstItem.length >= 5) {
                firstItem
            } else {
                "!$firstItem!"
            }.uppercase()
        }
    println(modifiedFirstItem)
}