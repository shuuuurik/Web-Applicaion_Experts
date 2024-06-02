package ru.ac.uniyar.domain.operations

import org.ktorm.database.Database
import org.ktorm.dsl.eq
import org.ktorm.dsl.update
import ru.ac.uniyar.domain.database.tables.AnnouncementTable

class EditAnnouncementOperation(
    private val database: Database,
) {
    fun edit(announcementId: Int, categoryId: Int, city: String, title: String, description: String) =
        database.update(AnnouncementTable) {
            set(it.categoryId, categoryId)
            set(it.city, city)
            set(it.title, title)
            set(it.description, description)
            where {
                it.id eq announcementId
            }
        }
}
