package me.study.myktlint.repository

import me.study.myktlint.domain.Todo

interface TodoRepository {
    fun save(todo: Todo): Todo
    fun findById(id: String): Todo?
    fun findAll(): List<Todo>
    fun update(todo: Todo): Todo?
    fun deleteById(id: String): Boolean
    fun existsById(id: String): Boolean
}