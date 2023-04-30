package lec09

class Person2(
    name: String = "jinseong",
    var age: Int = 1,
) {
    val name = name
        get() = field.uppercase()
}

fun main() {
    val person = Person2()
    println(person.name)
}