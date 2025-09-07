package lec22

import kotlin.collections.first

// 제네릭 타입 조건을 여러개 쓰고 싶으면 where + , 로 연결한다
class Cage5<T> where T : Animal, T : Comparable<T> {
    private val animals: MutableList<T> = mutableListOf()

    fun getFirst(): T = animals.first()

    fun put(animal: T) {
        this.animals.add(animal)
    }

    fun moveFrom(otherCage: Cage5<T>) {
        this.animals.addAll(otherCage.animals)
    }

    fun moveTo(otherCage: Cage5<T>) {
        otherCage.animals.addAll(this.animals)
    }
}
