package lec03

fun main() {
    val person = Person("황진성", 100)
    val log = "이름: ${person.name} / 나이: ${person.age}"
    println(log)
}