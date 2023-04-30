package lec12

class Person private constructor(
    private val name: String,
    private val age: Int,
) {
    companion object {
        private const val MIN_AGE: Int = 1

        @JvmStatic
        fun newBaby(name: String): Person {
            return Person(name, MIN_AGE)
        }
    }
}

fun main() {
    val person = Person.Companion.newBaby("js")
}