package lec22

class Cage3<out T> { // 클래스 자체를 공변으로 만듦
    private  val animals: MutableList<T> = mutableListOf()

    fun getFirst(): T = this.animals.first()

    fun getAll(): List<T> = this.animals

    // out으로 공변으로 만들었기 때문에 데이터 생산만 가능한데, 소비도 하고 싶다면
    // @UnsafeVariance로 예외적으로 데이터 소비(파라미터로 받기)를 허용해주면 된다
    fun contains(element: @UnsafeVariance T) {
        this.animals.contains(element)
    }
}

fun main() {
    val fishCage: Cage3<Fish> = Cage3()
    val animalCage: Cage3<Animal> = fishCage
}