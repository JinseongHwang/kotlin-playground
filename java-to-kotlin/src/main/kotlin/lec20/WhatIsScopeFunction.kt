package lec20

import lec19.Person

fun main() {
    val person = Person("jinseong", 100)

    val value1 = person.let {
        it.age
    }
    println("value1 => ${value1}")

    val value2 = person.run {
        this.age
    }
    println("value2 => ${value2}")

    val value3 = person.also {
        it.age
    }
    println("value3 => ${value3}")

    val value4 = person.apply {
        this.age
    }
    println("value4 => ${value4}")

    val value5 = with(person) {
        println("with => ${person} / 이름: ${name} / 나이: ${this.age}")
        "A"
    }
    println("value5 => ${value5}")
}

fun printPerson(person: Person?) {
    if (person != null) {
        println(person.name)
        println(person.age)
    }
}

fun printPersonV2(person: Person?) {
    person?.let {
        println(it.name)
        println(it.age)
    }
}