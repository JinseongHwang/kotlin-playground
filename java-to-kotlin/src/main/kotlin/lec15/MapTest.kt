package lec15

fun main() {
    val oldMap = mutableMapOf<Int, String>()
    oldMap.put(1, "Monday") // 방법1
    oldMap[2] = "Tuesday" // 방법2

    val map = mapOf(
        1 to "Monday",
        2 to "Tuesday",
    )

    for (key in map.keys) {
        println("$key : ${map[key]}")
    }

    for ((k, v) in map.entries) {
        println("$k : $v")
    }
}