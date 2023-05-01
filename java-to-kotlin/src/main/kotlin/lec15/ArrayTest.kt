package lec15

fun main() {
    val array = arrayOf(100, 200)
    val indices = array.indices
    println(indices) // 0..1 -> IntRange 객체
    for (i in indices) {
        println("${i} ${array[i]}")
    }

    val withIndex = array.withIndex()
    println(withIndex) // IndexingIterable 객체
    for ((idx, value) in withIndex) {
        println("${idx} ${value}")
    }
}