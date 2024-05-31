package ru.ac.uniyar.domain.operations

import org.ktorm.database.Database
import org.ktorm.dsl.insert
import ru.ac.uniyar.domain.database.tables.AnnouncementTable
import java.time.LocalDateTime

class AddAnnouncementOperation(
    private val database: Database,
) {
    fun add(
        category: String,
        title: String,
        city: String,
        description: String,
        specialistId: Int
    ) {
        database.insert(AnnouncementTable) {
            set(it.category, category)
            set(it.title, title)
            set(it.city, city)
            set(it.description, description)
            set(it.specialistId, specialistId)
            set(it.adding_time, LocalDateTime.now())
        }
    }
}
