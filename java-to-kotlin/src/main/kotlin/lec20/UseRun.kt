package lec20

import lec19.Person



fun main() {
    val personRepository = PersonRepository()
    val person = Person("jinseong", 100).run(personRepository::save)
    println(person)
}