package lec08

fun printNameAndGender(name: String, gender: String) {
    println("이름 = ${name}")
    println("성별 = ${gender}")
}

fun main() {
    printNameAndGender(
        name = "jinseong",
        gender = "male",
    )
}