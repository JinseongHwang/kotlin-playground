package lec18


fun main() {
    val fruitsInList: List<List<Fruit>> = listOf(
        listOf(
            Fruit(1, "사과", 1_000L),
            Fruit(2, "사과", 1_200L),
            Fruit(3, "사과", 1_200L),
            Fruit(4, "사과", 1_500L),
        ),
        listOf(
            Fruit(5, "바나나", 3_000L),
            Fruit(6, "바나나", 3_200L),
            Fruit(7, "바나나", 2_500L),
        ),
        listOf(
            Fruit(8, "수박", 10_000L),
        ),
    )


    val samePriceFruits = fruitsInList.flatMap { list -> list.samePriceFilter }

    val flatten = fruitsInList.flatten()

    println("FINISH")
}