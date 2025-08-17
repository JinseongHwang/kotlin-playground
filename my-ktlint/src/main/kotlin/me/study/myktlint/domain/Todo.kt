package me.study.myktlint.domain

import java.time.LocalDateTime

data class Todo(
    val id: String,
    val title: String,
    val description: String,
    val completed: Boolean = false,
    val createdAt: LocalDateTime = LocalDateTime.now(),
    val updatedAt: LocalDateTime = LocalDateTime.now()
)