package lec17

fun main() {
    val fruits = listOf(
        KoFruit("사과", 1_000),
        KoFruit("사과", 1_200),
        KoFruit("사과", 1_200),
        KoFruit("사과", 1_500),
        KoFruit("바나나", 3_000),
        KoFruit("바나나", 3_200),
        KoFruit("바나나", 2_500),
        KoFruit("수박", 10_000),
    )

    val isApple1: (KoFruit) -> Boolean = fun(fruit: KoFruit): Boolean {
        return fruit.name == "사과"
    }

    val isApple2: (KoFruit) -> Boolean = { fruit: KoFruit -> fruit.name == "사과" }

    println(isApple1(fruits[0]))
    println(isApple1.invoke(fruits[0]))
    println(isApple2(fruits[0]))
    println(isApple2.invoke(fruits[0]))

    val filtered1 = filterFruits(fruits, isApple1)
    val filtered2 = filterFruits(fruits, isApple2)
    val filtered3 = filterFruits(fruits, { fruit: KoFruit -> fruit.name == "사과" })
    val filtered4 = filterFruits(fruits) { fruit: KoFruit -> fruit.name == "사과" }
    val filtered5 = filterFruits(fruits) { fruit -> fruit.name == "사과" }
    val filtered6 = filterFruits(fruits) { it.name == "사과" }
    println("FIN")
}

private fun filterFruits(
    fruits: List<KoFruit>, filter: (KoFruit) -> Boolean
): List<KoFruit> {
    val results = mutableListOf<KoFruit>()
    for (fruit in fruits) {
        if (filter(fruit)) {
            results.add(fruit)
        }
    }
    return results
}