package me.study.section1.code1

fun main() {
    println("[${Thread.currentThread().name}] 시작!")
    Thread.sleep(1000)
    println("[${Thread.currentThread().name}] 종료!")
}