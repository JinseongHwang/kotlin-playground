package lec10

interface Swimable {

    val swimAbility: Int
    fun act() = println("어푸 어푸 내 수영 능력치는 ${swimAbility}야!")
}