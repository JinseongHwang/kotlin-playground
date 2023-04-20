package lec03

fun main() {

}

fun printAgeIfPerson1(obj: Any) {
    if (obj is Person) {
        val person = obj as Person
        println(person.age)
    }
}

fun printAgeIfPerson2(obj: Any) {
    if (obj !is Person) {
        throw IllegalArgumentException("obj is not Person type")
    }
}

fun printAgeIfPerson3(obj: Any?) {
    val person = obj as? Person
    println(person?.age)
}
