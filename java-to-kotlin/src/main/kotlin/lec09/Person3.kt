package lec09

class Person3(
    val name: String = "jinseong",
    var age: Int = 1,
) {
    val uppercaseName: String
        get() = this.name.uppercase()
}

fun main() {
    val person = Person3()
    println(person.uppercaseName)
}