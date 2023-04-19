package lec01

fun main() {

    var number1: Long = 10L // Variable: non-final
    val number2: Long = 10L // Value: final

    val number3 = 1_000L // not null
    var number4: Long? = null // nullable
    number4 = 1_000L

    println(number3.javaClass)
    println(number4.javaClass)

    var person = Person("jinseonghwang")
    println(person.name)
}