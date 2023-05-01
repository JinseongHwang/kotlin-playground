package lec14

data class PersonDto(
    val name: String,
    val age: Int,
)

fun main() {
    val dto1 = PersonDto("jsjs", 100)
    val dto2 = PersonDto("jsjs", 200)
    val dto3 = PersonDto("jsjs", 200)

    println(dto1 == dto2) // false
    println(dto2 == dto3) // true
    println(dto1) // PersonDto(name=jsjs, age=100)
}