package me.study.section2.code1

import kotlinx.coroutines.CoroutineName
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

/**
 * 아래 main 함수는 main 스레드에서 실행된다.
 * main 스레드를 점유하는 코루틴 1개를 만든다는 의미이다.
 * runBlocking으로 코루틴을 생성하면, 해당 코루틴이 완료될 때까지 이 코루틴과 무관한 다른 작업이
 * 이 main 스레드를 점유하지 못하도록 막는다는 특징이 있다.
 * 따라서 runBlocking은 처음 코루틴을 만들 때만 사용하고, 그 이후에는 launch나 async 등을 사용한다.
 */
fun main() = runBlocking<Unit>(CoroutineName("runBlocking 코루틴")) {
    println("[${Thread.currentThread().name}] runBlocking 코루틴 실행")

    launch(CoroutineName("launch 코루틴")) {
        println("[${Thread.currentThread().name}] launch 코루틴 실행")
    }
}