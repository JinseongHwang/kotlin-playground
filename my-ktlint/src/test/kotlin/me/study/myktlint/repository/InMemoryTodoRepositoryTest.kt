package me.study.myktlint.repository

import me.study.myktlint.domain.Todo
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import java.util.UUID

class InMemoryTodoRepositoryTest {
    private lateinit var repository: InMemoryTodoRepository
    
    @BeforeEach
    fun setUp() {
        repository = InMemoryTodoRepository()
    }
    
    @Test
    fun `should save and retrieve todo`() {
        val todo = Todo(
            id = UUID.randomUUID().toString(),
            title = "Test Todo",
            description = "Test Description"
        )
        
        val savedTodo = repository.save(todo)
        assertEquals(todo, savedTodo)
        
        val retrievedTodo = repository.findById(todo.id)
        assertNotNull(retrievedTodo)
        assertEquals(todo.id, retrievedTodo?.id)
        assertEquals(todo.title, retrievedTodo?.title)
    }
    
    @Test
    fun `should return null when todo not found`() {
        val result = repository.findById("non-existent")
        assertNull(result)
    }
    
    @Test
    fun `should return all todos`() {
        val todo1 = Todo(
            id = "1",
            title = "Todo 1",
            description = "Description 1"
        )
        val todo2 = Todo(
            id = "2",
            title = "Todo 2",
            description = "Description 2"
        )
        
        repository.save(todo1)
        repository.save(todo2)
        
        val todos = repository.findAll()
        assertEquals(2, todos.size)
        assertTrue(todos.contains(todo1))
        assertTrue(todos.contains(todo2))
    }
    
    @Test
    fun `should update existing todo`() {
        val todo = Todo(
            id = "1",
            title = "Original",
            description = "Original Description"
        )
        repository.save(todo)
        
        val updatedTodo = todo.copy(title = "Updated")
        val result = repository.update(updatedTodo)
        
        assertNotNull(result)
        assertEquals("Updated", result?.title)
        
        val retrieved = repository.findById("1")
        assertEquals("Updated", retrieved?.title)
    }
    
    @Test
    fun `should return null when updating non-existent todo`() {
        val todo = Todo(
            id = "non-existent",
            title = "Test",
            description = "Test"
        )
        
        val result = repository.update(todo)
        assertNull(result)
    }
    
    @Test
    fun `should delete todo by id`() {
        val todo = Todo(
            id = "1",
            title = "To Delete",
            description = "Will be deleted"
        )
        repository.save(todo)
        
        val deleted = repository.deleteById("1")
        assertTrue(deleted)
        
        val retrieved = repository.findById("1")
        assertNull(retrieved)
    }
    
    @Test
    fun `should return false when deleting non-existent todo`() {
        val result = repository.deleteById("non-existent")
        assertFalse(result)
    }
    
    @Test
    fun `should check if todo exists`() {
        val todo = Todo(
            id = "1",
            title = "Test",
            description = "Test"
        )
        repository.save(todo)
        
        assertTrue(repository.existsById("1"))
        assertFalse(repository.existsById("non-existent"))
    }
}