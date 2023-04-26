package lec09

class Person(
    val name: String = "진송",
    var age: Int = 1,
) {
    init {
        if (age <= 0) {
            throw IllegalArgumentException("나이는 ${age} 일 수 없습니다.")
        }
    }

    // 부생성자 1
    constructor(name: String): this(name, 1)

    // 부생성자 2
    constructor() : this("진송") {
        println("부생성자2 호출 -> 부생성자1 호출 -> 주생성자 호출")
    }
}

fun main() {
    val person = Person("진성", 100)
    println(person.name)
    person.age = 10
    println(person.age)
}