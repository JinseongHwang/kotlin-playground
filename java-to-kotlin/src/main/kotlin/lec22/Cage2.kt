package lec22

class Cage2<T> {
    private val animals: MutableList<T> = mutableListOf()

    fun getFirst(): T = animals.first()

    fun put(animal: T) {
        this.animals.add(animal)
    }

    fun moveFrom(otherCage: Cage2<out T>) {
        this.animals.addAll(otherCage.animals)

        // out 을 붙이면 파라미터가 데이터 생산자 역할만 할 수 있다.
//        otherCage.getFirst() // out을 사용하면 조회는 가능
//        otherCage.put(GoldFish("금붕어")) // out을 사용하면 저장 불가능!
    }
}

fun main() {
    val goldFishCage = Cage2<GoldFish>()
    goldFishCage.put(GoldFish("금붕어"))

    val cage = Cage2<Fish>()
    cage.moveFrom(goldFishCage)

    val fish: Fish = cage.getFirst()
}