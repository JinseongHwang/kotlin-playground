package lec12

private fun moveSomething(movable: Movable) {
    movable.move()
    movable.fly()
}

fun main() {
    moveSomething(object : Movable {
        override fun move() {
            println("무빙 슈슈슉")
        }
        override fun fly() {
            println("플라잉 파닥파닥")
        }
    })
}