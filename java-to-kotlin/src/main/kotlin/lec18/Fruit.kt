package lec18


data class Fruit(
    val id: Long,
    val name: String,
    var factoryPrice: Long?, // 원가
    var currentPrice: Long? = (factoryPrice?.times(1.0)?.toLong()) // 원가 + VAT
) {
    val isSamePrice: Boolean
        get() {
            if (factoryPrice == null || currentPrice == null) {
                return false
            }
            return factoryPrice == currentPrice
        }
}

val List<Fruit>.samePriceFilter: List<Fruit>
    get() = this.filter(Fruit::isSamePrice)

fun main3() {
    val fruits = listOf(
        Fruit(1, "사과", 1_000L),
        Fruit(2, "사과", 1_200L),
        Fruit(3, "사과", 1_200L),
        Fruit(4, "사과", 1_500L),
        Fruit(5, "사과", null),
        Fruit(6, "바나나", 3_000L),
        Fruit(7, "바나나", 3_200L),
        Fruit(8, "바나나", 2_500L),
        Fruit(9, "수박", 10_000L),
    )

    // 사과만 주세요!
    val apples1 = fruits.filter { fruit: Fruit -> fruit.name == "사과" }

    // 사과만 주세요... 근데 인덱스도 필요한데?
    val apples2 = fruits.filterIndexed { idx, fruit ->
        println(idx)
        fruit.name == "사과"
    }

    // 사과의 가격들만 알려주세요
    val applePrices1 = fruits.filter { fruit: Fruit -> fruit.name == "사과" }
        .map { fruit: Fruit -> fruit.currentPrice }


    // 맵에서 인덱스가 필요한데?
    val applePrices2 = fruits.filter { fruit: Fruit -> fruit.name == "사과" }
        .mapIndexed { idx, fruit ->
            println(idx)
            fruit.currentPrice
        }

    // 사과의 가격들만 알고 싶은데, null인건 빼고 알려주세요.
    val values = fruits.filter { fruit: Fruit -> fruit.name == "사과" }
        .mapNotNull { fruit: Fruit -> fruit.currentPrice }

    // fruits 안에 있는데 모두 사과인가요?
    val isAllApple = fruits.all { fruit: Fruit -> fruit.name == "사과" }

    // fruits 안에 사과가 단 하나도 없나요?
    val isNoApple = fruits.none { fruit: Fruit -> fruit.name == "사과" }

    // 사과가 단 하나라도 있나요?
    val isAnyApple = fruits.any { fruit: Fruit -> fruit.name == "사과" }

    // 과일이 총 몇개인가요?
    val count = fruits.count()

    // 과일 가격 순으로 오름차순 정렬해서 보여주세요.
    // null 은 가장 작게 취급된다.
    val fruitOrderByPriceAsc = fruits.sortedBy { fruit: Fruit -> fruit.currentPrice }

    // 과일 가격 순으로 내림차순 정렬해서 보여주세요.
    // 마찬가지로 null 은 가장 작게 취급된다.
    val fruitOrderByPriceDesc = fruits.sortedByDescending { fruit: Fruit -> fruit.currentPrice }

    // 과일 이름으로 중복 제거해서 보여주세요.
    val distinctFruitNames = fruits.distinctBy { fruit: Fruit -> fruit.name }
        .map { fruit: Fruit -> fruit.name }

    val firstFruit1 = fruits.first()
    val firstFruit2 = fruits.firstOrNull()
    val lastFruit1 = fruits.last()
    val lastFruit2 = fruits.lastOrNull()

    // 과일 이름으로 그룹핑해주세요.
    val map1: Map<String, List<Fruit>> = fruits.groupBy { fruit: Fruit -> fruit.name }

    // 중복되지 않는 key를 가지고 맵을 만들고 싶어요.
    val map2: Map<Long, Fruit> = fruits.associateBy { fruit: Fruit -> fruit.id }

    // 과일 이름으로 그룹핑하고 원가만 필요해요.
    // groupBy() 안에서 Key와 Value를 동시에 정의할 수 있다.
    val map3: Map<String, List<Long>> = fruits
        .groupBy({ fruit: Fruit -> fruit.name}, {fruit: Fruit -> fruit.factoryPrice ?: -1 })

    println("FINISH")
}

