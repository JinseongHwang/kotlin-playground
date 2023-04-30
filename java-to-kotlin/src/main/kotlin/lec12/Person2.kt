package lec12

class Person2 private constructor(
    private val name: String,
    private val age: Int,
) {
    companion object Factory : Log {
        private const val MIN_AGE: Int = 1
        fun newBaby(name: String): Person2 {
            return Person2(name, MIN_AGE)
        }

        override fun log() {
            println("나는 Person2 클래스의 companion object 입니다~")
        }
    }
}

fun main() {
    Person2.Factory.newBaby("js")
}