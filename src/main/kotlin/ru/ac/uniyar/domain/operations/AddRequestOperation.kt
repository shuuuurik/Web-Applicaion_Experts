package ru.ac.uniyar.domain.operations

import org.ktorm.database.Database
import org.ktorm.dsl.insert
import ru.ac.uniyar.domain.database.tables.RequestTable
import java.time.LocalDateTime

class AddRequestOperation(
    private val database: Database,
) {
    fun add(
        username: String,
        categoryId: Int,
        education: String,
        experience: String,
    ) {
        database.insert(RequestTable) {
            set(it.username, username)
            set(it.categoryId, categoryId)
            set(it.education, education)
            set(it.experience, experience)
            set(it.status, "Новая")
            set(it.addingTime, LocalDateTime.now())
        }
    }
}
