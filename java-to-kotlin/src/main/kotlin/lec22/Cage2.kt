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
    val cage = Cage2<Carp>()
    cage.put(Carp("잉어"))
    val carp: Carp = cage.getFirst() // Carp 타입이 바로 나온다
}