package me.study.section7

import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

/**
 * 구조화된 동시성의 원칙이란 비동기 작업을 구조화해 비동기 프로그래밍을 보다 안정적이고, 예측 가능하게 만드는 원칙
 * 코루틴은 구조화된 동시성의 원칙을 사용해 비동기 작업인 코루틴을 부모-자식 관계로 구조화해 코루틴이 보다 안전하게 관리되고 제어될 수 있도록 한다.
 * 코루틴을 부모-자식 관계로 구조화 하기 위해서는 코루틴 빌더 함수의 람다식 안에서 새로운 코루틴 빌더 함수를 호출하기만 하면 된다
 */
fun main() = runBlocking<Unit> {
    println("[${Thread.currentThread().name}] runBlocking 코루틴 실행") // 1등
    launch {
        println("[${Thread.currentThread().name}] 부모 코루틴 실행") // 2등
        launch {
            println("[${Thread.currentThread().name}] 자식 코루틴 실행") // 3등
        }
    }
    println("[${Thread.currentThread().name}] 1,2,3 등은 모두 계층 관계를 띈다")
}