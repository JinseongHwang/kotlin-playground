package lec20

data class Person(
    val name: String,
    val age: Int,
    var hobby: String? = null,
)

fun createPerson(
    name: String,
    age: Int,
    hobby: String,
): Person {
    return Person(
        name = name,
        age = age,
    ).apply {
        this.hobby = hobby
    }
}

fun main() {
    val person = createPerson("jinseong", 100, "programming")
    println(person)
}
