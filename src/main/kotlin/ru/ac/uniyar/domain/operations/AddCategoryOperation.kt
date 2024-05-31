package ru.ac.uniyar.domain.operations

import org.ktorm.database.Database
import org.ktorm.dsl.insert
import ru.ac.uniyar.domain.database.tables.CategoryTable
import java.time.LocalDateTime

class AddCategoryOperation(
    private val database: Database,
) {
    fun add(name: String) {
        database.insert(CategoryTable) {
            set(it.name, name)
            set(it.adding_time, LocalDateTime.now())
        }
    }
}
