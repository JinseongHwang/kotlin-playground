package lec22

class Cage {
    private val animals: MutableList<Animal> = mutableListOf()

    fun getFirst(): Animal = animals.first()

    fun put(animal: Animal) {
        this.animals.add(animal)
    }

    fun moveFrom(cage: Cage) {
        this.animals.addAll(cage.animals)
    }
}

fun main() {
    val cage = Cage()
    cage.put(Carp("잉어"))
    val carp1: Animal = cage.getFirst() // 문제: Carp 타입이 아니라 Animal 타입이다.
    val carp2: Carp = cage.getFirst() as Carp // 방법1: as 를 사용해서 타입 캐스팅. 런타임 에러 위험이 있다.
    val carp3: Carp = cage.getFirst() as? Carp
        ?: throw IllegalArgumentException() // 방법2: ?: + throw를 사용해서 safe 타입 캐스팅
}