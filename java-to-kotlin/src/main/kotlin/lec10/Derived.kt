package lec10

class Derived(
    override val number: Int
): Base(number) {
    init {
        println("Derived Class")
        println(number)
    }
}

fun main() {
    val obj = Derived(3)
}