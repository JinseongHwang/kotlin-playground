package lec02

fun main() {
    val person = Person("sdf")
    startsWithA(person.name)
}

fun startsWithA(str: String): Boolean {
    return str.startsWith("A")
}