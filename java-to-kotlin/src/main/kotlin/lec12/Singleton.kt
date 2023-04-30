package lec12

object Singleton {
    var a: Int = 0
}

fun main() {
    println(Singleton.a)
    Singleton.a = 10
    println(Singleton.a)
}