package me.study.myktlint.controller

import me.study.myktlint.domain.Todo
import me.study.myktlint.dto.CreateTodoRequest
import me.study.myktlint.dto.UpdateTodoRequest
import me.study.myktlint.service.TodoService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/todos")
class TodoController(
    private val todoService: TodoService
) {
    @GetMapping
    fun getAllTodos(): ResponseEntity<List<Todo>> {
        return ResponseEntity.ok(todoService.getAllTodos())
    }

    @GetMapping("/{id}")
    fun getTodoById(@PathVariable id: String): ResponseEntity<Todo> {
        return todoService.getTodoById(id)?.let {
            ResponseEntity.ok(it)
        } ?: ResponseEntity.notFound().build()
    }

    @PostMapping
    fun createTodo(@RequestBody request: CreateTodoRequest): ResponseEntity<Todo> {
        val todo = todoService.createTodo(request.title, request.description)
        return ResponseEntity.status(HttpStatus.CREATED).body(todo)
    }

    @PutMapping("/{id}")
    fun updateTodo(
        @PathVariable id: String,
        @RequestBody request: UpdateTodoRequest
    ): ResponseEntity<Todo> {
        return todoService.updateTodo(
            id = id,
            title = request.title,
            description = request.description,
            completed = request.completed
        )?.let {
            ResponseEntity.ok(it)
        } ?: ResponseEntity.notFound().build()
    }

    @PatchMapping("/{id}/toggle")
    fun toggleTodoCompletion(@PathVariable id: String): ResponseEntity<Todo> {
        return todoService.toggleTodoCompletion(id)?.let {
            ResponseEntity.ok(it)
        } ?: ResponseEntity.notFound().build()
    }

    @DeleteMapping("/{id}")
    fun deleteTodo(@PathVariable id: String): ResponseEntity<Void> {
        return if (todoService.deleteTodo(id)) {
            ResponseEntity.noContent().build()
        } else {
            ResponseEntity.notFound().build()
        }
    }
}