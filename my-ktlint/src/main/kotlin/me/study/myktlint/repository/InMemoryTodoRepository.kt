package me.study.myktlint.repository

import me.study.myktlint.domain.Todo
import org.springframework.stereotype.Repository
import java.util.concurrent.ConcurrentHashMap

@Repository
class InMemoryTodoRepository : TodoRepository {
    private val storage = ConcurrentHashMap<String, Todo>()

    override fun save(todo: Todo): Todo {
        storage[todo.id] = todo
        return todo
    }

    override fun findById(id: String): Todo? {
        return storage[id]
    }

    override fun findAll(): List<Todo> {
        return storage.values.toList()
    }

    override fun update(todo: Todo): Todo? {
        return if (storage.containsKey(todo.id)) {
            storage[todo.id] = todo
            todo
        } else {
            null
        }
    }

    override fun deleteById(id: String): Boolean {
        return storage.remove(id) != null
    }

    override fun existsById(id: String): Boolean {
        return storage.containsKey(id)
    }
}