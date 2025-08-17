package me.study.myktlint.service

import me.study.myktlint.domain.Todo
import me.study.myktlint.repository.TodoRepository
import org.springframework.stereotype.Service
import java.time.LocalDateTime
import java.util.UUID

@Service
class TodoService(
    private val todoRepository: TodoRepository
) {
    fun createTodo(title: String, description: String): Todo {
        val todo = Todo(
            id = UUID.randomUUID().toString(),
            title = title,
            description = description
        )
        return todoRepository.save(todo)
    }

    fun getTodoById(id: String): Todo? {
        return todoRepository.findById(id)
    }

    fun getAllTodos(): List<Todo> {
        return todoRepository.findAll()
    }

    fun updateTodo(id: String, title: String? = null, description: String? = null, completed: Boolean? = null): Todo? {
        val existingTodo = todoRepository.findById(id) ?: return null
        
        val updatedTodo = existingTodo.copy(
            title = title ?: existingTodo.title,
            description = description ?: existingTodo.description,
            completed = completed ?: existingTodo.completed,
            updatedAt = LocalDateTime.now()
        )
        
        return todoRepository.update(updatedTodo)
    }

    fun deleteTodo(id: String): Boolean {
        return todoRepository.deleteById(id)
    }

    fun toggleTodoCompletion(id: String): Todo? {
        val todo = todoRepository.findById(id) ?: return null
        val updatedTodo = todo.copy(
            completed = !todo.completed,
            updatedAt = LocalDateTime.now()
        )
        return todoRepository.update(updatedTodo)
    }
}