package ru.ac.uniyar.domain.operations

import org.ktorm.database.Database
import org.ktorm.dsl.insert
import ru.ac.uniyar.domain.database.tables.AnnouncementTable
import java.time.LocalDateTime

class AddAnnouncementOperation(
    private val database: Database,
) {
    fun add(
        categoryId: Int,
        title: String,
        city: String,
        description: String,
        specialistUsername: String,
    ) {
        database.insert(AnnouncementTable) {
            set(it.categoryId, categoryId)
            set(it.title, title)
            set(it.city, city)
            set(it.description, description)
            set(it.specialistUsername, specialistUsername)
            set(it.addingTime, LocalDateTime.now())
        }
    }
}
