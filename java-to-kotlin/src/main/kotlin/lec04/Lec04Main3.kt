package lec04

fun main() {
    val money1 = JavaMoney(1_000L)
    val money2 = money1
    val money3 = JavaMoney(1_000L)

    println(money1 === money2) // true
    println(money1 == money2) // true

    println(money1 === money3) // false
    println(money1 == money3) // true
}