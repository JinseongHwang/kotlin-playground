package lec22

import kotlin.collections.first

// Any 로 제약을 걸어서 Non-null 타입 제한을 걸 수 있다
class Cage6<T : Any> {
    private val animals: MutableList<T> = mutableListOf()

    fun getFirst(): T = animals.first()

    fun put(animal: T) {
        this.animals.add(animal)
    }

    fun moveFrom(otherCage: Cage6<T>) {
        this.animals.addAll(otherCage.animals)
    }

    fun moveTo(otherCage: Cage6<T>) {
        otherCage.animals.addAll(this.animals)
    }
}

fun main() {
    Cage6<String>() // 가능
//    Cage6<String?>() // 불가능
}