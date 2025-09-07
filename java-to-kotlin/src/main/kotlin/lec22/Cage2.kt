package lec22

class Cage2<T> {
    private val animals: MutableList<T> = mutableListOf()

    fun getFirst(): T = animals.first()

    fun put(animal: T) {
        this.animals.add(animal)
    }

    fun moveFrom(otherCage: Cage2<out T>) {
        this.animals.addAll(otherCage.animals)

        // out 을 붙이면 파라미터가 데이터 생산자 역할만 할 수 있다. 공변화 한다.
//        otherCage.getFirst() // out을 사용하면 조회는 가능
//        otherCage.put(GoldFish("금붕어")) // out을 사용하면 저장 불가능!
    }

    fun moveTo(otherCage: Cage2<in T>) {
        otherCage.animals.addAll(this.animals)
//        val fish: Fish = otherCage.getFirst()

        // in이 붙은 otherCage는 데이터를 받을 수만 있다! 반공변화 한다.
        // otherCage는 데이터 소비자이다.
    }
}

fun main() {
    val fishCage = Cage2<Fish>()

    val goldFishCage = Cage2<GoldFish>()
    goldFishCage.put(GoldFish("금붕어"))
    goldFishCage.moveTo(fishCage)
}