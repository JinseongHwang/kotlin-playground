package lec20

import lec19.Person

data class PersonDto(
    val name: String,
    val age: Int,
)

fun toPersonDtoV1(person: Person): PersonDto {
    return PersonDto(
        name = person.name,
        age = person.age,
    )
}

fun toPersonDtoV2(person: Person): PersonDto {
    return with(person) {
        PersonDto(
            name = name,
            age = age,
        )
    }
}

fun main() {

}