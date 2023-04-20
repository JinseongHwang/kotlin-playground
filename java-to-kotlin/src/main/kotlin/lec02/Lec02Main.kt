package lec02

fun main() {
    val str1: String? = "ABC"
    val str2: String? = null
    println(str1?.length)
    println(str2?.length)
    println(str2?.length ?: 0)

    println(startsWith("ABC"))
    println(startsWith(null))
}

fun startsWithA1(str: String?): Boolean {
    // str이 null이면 예외를 발생시킨다.
    return str?.startsWith("A")
        ?: throw IllegalArgumentException("null이 들어왔습니다")
}

fun startsWithA2(str: String?): Boolean? {
    // str이 null이면 null을 반환한다.
    return str?.startsWith("A")
}

fun startsWithA3(str: String?): Boolean {
    // str이 null이면 false를 반환한다.
    return str?.startsWith("A") ?: false
}

fun startsWith(str: String?): Boolean {
    return str!!.startsWith("A")
}