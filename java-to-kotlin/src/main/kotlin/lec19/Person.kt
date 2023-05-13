package lec19

data class Person(
    val name: String,
    val age: Int,
)

fun main() {
    val person = Person("jinseong", 20)
    val (name, age) = person
    println("이름 = $name / 나이 = $age")

    println(person.component1())
    println(person.component2())
}