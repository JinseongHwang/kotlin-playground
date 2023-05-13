package lec19

class Person2(
    val name: String,
    val age: Int,
) {
    operator fun component1(): String {
        return this.name
    }

    operator fun component2(): Int {
        return this.age
    }
}

fun main() {
    val person = Person2("jinseong", 1000)
    val (name, age) = person
    println("이름 = $name / 나이 = $age") // 이름 = jinseong / 나이 = 1000
}