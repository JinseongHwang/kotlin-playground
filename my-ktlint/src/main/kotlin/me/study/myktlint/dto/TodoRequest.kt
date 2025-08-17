package me.study.myktlint.dto

data class CreateTodoRequest(
    val title: String,
    val description: String
)

data class UpdateTodoRequest(
    val title: String? = null,
    val description: String? = null,
    val completed: Boolean? = null
)