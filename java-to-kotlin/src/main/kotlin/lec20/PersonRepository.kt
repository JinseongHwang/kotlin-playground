package lec20

import lec19.Person

class PersonRepository {
    fun save(person: Person): Person {
        println("Save successfully => ${person}")
        return person
    }
}