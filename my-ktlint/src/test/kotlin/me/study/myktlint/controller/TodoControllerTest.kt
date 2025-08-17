package me.study.myktlint.controller

import com.fasterxml.jackson.databind.ObjectMapper
import me.study.myktlint.domain.Todo
import me.study.myktlint.dto.CreateTodoRequest
import me.study.myktlint.dto.UpdateTodoRequest
import me.study.myktlint.service.TodoService
import org.junit.jupiter.api.Test
import org.mockito.kotlin.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.test.context.bean.override.mockito.MockitoBean
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.*

@WebMvcTest(TodoController::class)
class TodoControllerTest {
    @Autowired
    private lateinit var mockMvc: MockMvc
    
    @Autowired
    private lateinit var objectMapper: ObjectMapper
    
    @MockitoBean
    private lateinit var todoService: TodoService
    
    @Test
    fun `should get all todos`() {
        val todos = listOf(
            Todo(id = "1", title = "Todo 1", description = "Desc 1"),
            Todo(id = "2", title = "Todo 2", description = "Desc 2")
        )
        
        whenever(todoService.getAllTodos()).thenReturn(todos)
        
        mockMvc.perform(get("/api/todos"))
            .andExpect(status().isOk)
            .andExpect(jsonPath("$[0].id").value("1"))
            .andExpect(jsonPath("$[0].title").value("Todo 1"))
            .andExpect(jsonPath("$[1].id").value("2"))
            .andExpect(jsonPath("$[1].title").value("Todo 2"))
    }
    
    @Test
    fun `should get todo by id`() {
        val todo = Todo(id = "1", title = "Test Todo", description = "Test Description")
        
        whenever(todoService.getTodoById("1")).thenReturn(todo)
        
        mockMvc.perform(get("/api/todos/1"))
            .andExpect(status().isOk)
            .andExpect(jsonPath("$.id").value("1"))
            .andExpect(jsonPath("$.title").value("Test Todo"))
            .andExpect(jsonPath("$.description").value("Test Description"))
    }
    
    @Test
    fun `should return 404 when todo not found`() {
        whenever(todoService.getTodoById("non-existent")).thenReturn(null)
        
        mockMvc.perform(get("/api/todos/non-existent"))
            .andExpect(status().isNotFound)
    }
    
    @Test
    fun `should create todo`() {
        val request = CreateTodoRequest(
            title = "New Todo",
            description = "New Description"
        )
        val todo = Todo(
            id = "generated-id",
            title = request.title,
            description = request.description
        )
        
        whenever(todoService.createTodo(request.title, request.description)).thenReturn(todo)
        
        mockMvc.perform(
            post("/api/todos")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request))
        )
            .andExpect(status().isCreated)
            .andExpect(jsonPath("$.id").value("generated-id"))
            .andExpect(jsonPath("$.title").value("New Todo"))
            .andExpect(jsonPath("$.description").value("New Description"))
    }
    
    @Test
    fun `should update todo`() {
        val request = UpdateTodoRequest(
            title = "Updated Todo",
            completed = true
        )
        val updatedTodo = Todo(
            id = "1",
            title = "Updated Todo",
            description = "Original Description",
            completed = true
        )
        
        whenever(todoService.updateTodo("1", request.title, request.description, request.completed))
            .thenReturn(updatedTodo)
        
        mockMvc.perform(
            put("/api/todos/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request))
        )
            .andExpect(status().isOk)
            .andExpect(jsonPath("$.id").value("1"))
            .andExpect(jsonPath("$.title").value("Updated Todo"))
            .andExpect(jsonPath("$.completed").value(true))
    }
    
    @Test
    fun `should return 404 when updating non-existent todo`() {
        val request = UpdateTodoRequest(title = "Updated")
        
        whenever(todoService.updateTodo("non-existent", request.title, request.description, request.completed))
            .thenReturn(null)
        
        mockMvc.perform(
            put("/api/todos/non-existent")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request))
        )
            .andExpect(status().isNotFound)
    }
    
    @Test
    fun `should toggle todo completion`() {
        val toggledTodo = Todo(
            id = "1",
            title = "Test",
            description = "Test",
            completed = true
        )
        
        whenever(todoService.toggleTodoCompletion("1")).thenReturn(toggledTodo)
        
        mockMvc.perform(patch("/api/todos/1/toggle"))
            .andExpect(status().isOk)
            .andExpect(jsonPath("$.id").value("1"))
            .andExpect(jsonPath("$.completed").value(true))
    }
    
    @Test
    fun `should delete todo`() {
        whenever(todoService.deleteTodo("1")).thenReturn(true)
        
        mockMvc.perform(delete("/api/todos/1"))
            .andExpect(status().isNoContent)
    }
    
    @Test
    fun `should return 404 when deleting non-existent todo`() {
        whenever(todoService.deleteTodo("non-existent")).thenReturn(false)
        
        mockMvc.perform(delete("/api/todos/non-existent"))
            .andExpect(status().isNotFound)
    }
}