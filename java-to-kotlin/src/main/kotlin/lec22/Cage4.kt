package lec22

// Animal 하위 타입만 제네릭 타입으로 쓸 수 있다.
class Cage4<T : Animal> {
    private val animals: MutableList<T> = mutableListOf()

    fun getFirst(): T = animals.first()

    fun put(animal: T) {
        this.animals.add(animal)
    }

    fun moveFrom(otherCage: Cage4<T>) {
        this.animals.addAll(otherCage.animals)
    }

    fun moveTo(otherCage: Cage4<T>) {
        otherCage.animals.addAll(this.animals)
    }
}
