package me.study.chapter18

fun main() {
    processList()
    processSequence()
}

private fun processList() {
    val l = buildList {
        repeat(3) {
            add("User$it")
            println("L: Added User")
        }
    }

    val l2 = l.map {
        println("L: Processing")
        "Processed $it"
    }
}

private fun processSequence() {
    val s = sequence {
        repeat(3) {
            yield("User$it")
            println("S: Added User")
        }
    }

    val s2: Sequence<String> = s.map {
        println("S: Processing")
        "Processed $it"
    }
//    s2.toList() // 종단 연산을 호출하기 전까지 Sequence는 연산식일 뿐이다.
}