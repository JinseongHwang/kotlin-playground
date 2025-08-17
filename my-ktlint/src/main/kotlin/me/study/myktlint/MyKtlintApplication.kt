package me.study.myktlint

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class MyKtlintApplication

fun main(args: Array<String>) {
    runApplication<MyKtlintApplication>(*args)
}
