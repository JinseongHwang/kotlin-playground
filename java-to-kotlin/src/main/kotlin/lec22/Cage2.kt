package lec22

class Cage2<T> {
    private val animals: MutableList<T> = mutableListOf()

    fun getFirst(): T = animals.first()

    fun put(animal: T) {
        this.animals.add(animal)
    }

    fun moveFrom(cage: Cage2<T>) {
        this.animals.addAll(cage.animals)
    }
}

fun main() {
    val goldFishCage = Cage2<GoldFish>()
    goldFishCage.put(GoldFish("금붕어"))

    val cage = Cage2<Fish>()
//    cage.moveFrom(goldFishCage) // Error: Type mismatch
}