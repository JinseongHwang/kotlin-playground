package lec19

fun getNumberOrNull(number: Int): Int? {
    return if (number <= 0) {
        null
    } else {
        number
    }
}

fun getNumberOrNullV2(number: Int): Int? {
    return number.takeIf { it > 0 }
}

fun getNumberOrNullV3(number: Int): Int? {
    return number.takeUnless { it <= 0 }
}

fun main() {
    // v1: original
    println(getNumberOrNull(-1)) // null
    println(getNumberOrNull(10)) // 10

    // v2: takeIf
    println(getNumberOrNullV2(-1)) // null
    println(getNumberOrNullV2(10)) // 10

    // v3: takeUnless
    println(getNumberOrNullV3(-1)) // null
    println(getNumberOrNullV3(10)) // 10
}

