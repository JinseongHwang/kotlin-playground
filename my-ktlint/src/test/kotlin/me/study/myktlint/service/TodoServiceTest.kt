package me.study.myktlint.service

import me.study.myktlint.domain.Todo
import me.study.myktlint.repository.TodoRepository
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.Mock
import org.mockito.junit.jupiter.MockitoExtension
import org.mockito.kotlin.*
import java.time.LocalDateTime

@ExtendWith(MockitoExtension::class)
class TodoServiceTest {
    @Mock
    private lateinit var todoRepository: TodoRepository
    
    private lateinit var todoService: TodoService
    
    @BeforeEach
    fun setUp() {
        todoService = TodoService(todoRepository)
    }
    
    @Test
    fun `should create todo with generated id`() {
        val title = "New Todo"
        val description = "New Description"
        
        whenever(todoRepository.save(any())).thenAnswer { it.arguments[0] }
        
        val result = todoService.createTodo(title, description)
        
        assertNotNull(result.id)
        assertEquals(title, result.title)
        assertEquals(description, result.description)
        assertFalse(result.completed)
        
        verify(todoRepository).save(any())
    }
    
    @Test
    fun `should get todo by id`() {
        val todo = Todo(
            id = "1",
            title = "Test",
            description = "Test Description"
        )
        
        whenever(todoRepository.findById("1")).thenReturn(todo)
        
        val result = todoService.getTodoById("1")
        
        assertNotNull(result)
        assertEquals(todo, result)
        verify(todoRepository).findById("1")
    }
    
    @Test
    fun `should return null when todo not found`() {
        whenever(todoRepository.findById("non-existent")).thenReturn(null)
        
        val result = todoService.getTodoById("non-existent")
        
        assertNull(result)
        verify(todoRepository).findById("non-existent")
    }
    
    @Test
    fun `should get all todos`() {
        val todos = listOf(
            Todo(id = "1", title = "Todo 1", description = "Desc 1"),
            Todo(id = "2", title = "Todo 2", description = "Desc 2")
        )
        
        whenever(todoRepository.findAll()).thenReturn(todos)
        
        val result = todoService.getAllTodos()
        
        assertEquals(2, result.size)
        assertEquals(todos, result)
        verify(todoRepository).findAll()
    }
    
    @Test
    fun `should update todo`() {
        val existingTodo = Todo(
            id = "1",
            title = "Original",
            description = "Original Desc",
            completed = false,
            createdAt = LocalDateTime.now().minusDays(1)
        )
        
        whenever(todoRepository.findById("1")).thenReturn(existingTodo)
        whenever(todoRepository.update(any())).thenAnswer { it.arguments[0] }
        
        val result = todoService.updateTodo("1", title = "Updated", completed = true)
        
        assertNotNull(result)
        assertEquals("Updated", result?.title)
        assertEquals("Original Desc", result?.description)
        assertTrue(result?.completed ?: false)
        
        verify(todoRepository).findById("1")
        verify(todoRepository).update(any())
    }
    
    @Test
    fun `should return null when updating non-existent todo`() {
        whenever(todoRepository.findById("non-existent")).thenReturn(null)
        
        val result = todoService.updateTodo("non-existent", title = "Updated")
        
        assertNull(result)
        verify(todoRepository).findById("non-existent")
        verify(todoRepository, never()).update(any())
    }
    
    @Test
    fun `should delete todo`() {
        whenever(todoRepository.deleteById("1")).thenReturn(true)
        
        val result = todoService.deleteTodo("1")
        
        assertTrue(result)
        verify(todoRepository).deleteById("1")
    }
    
    @Test
    fun `should toggle todo completion`() {
        val todo = Todo(
            id = "1",
            title = "Test",
            description = "Test",
            completed = false
        )
        
        whenever(todoRepository.findById("1")).thenReturn(todo)
        whenever(todoRepository.update(any())).thenAnswer { it.arguments[0] }
        
        val result = todoService.toggleTodoCompletion("1")
        
        assertNotNull(result)
        assertTrue(result?.completed ?: false)
        
        verify(todoRepository).findById("1")
        verify(todoRepository).update(any())
    }
}