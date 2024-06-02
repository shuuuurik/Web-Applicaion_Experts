package ru.ac.uniyar.domain.database.entities

import org.ktorm.dsl.QueryRowSet
import ru.ac.uniyar.domain.database.tables.AnnouncementTable
import ru.ac.uniyar.domain.database.tables.CategoryTable
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@Suppress("LongParameterList")
class AnnouncementWithCategoryId(
    override val id: Int,
    override val category: String,
    val categoryId: Int,
    override val city: String,
    override val title: String,
    override val description: String,
    override val specialistUsername: String,
    override val addingTime: String = LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd.MM.yyyy, HH:mm"))
) : Announcement(id, category, city, title, description, specialistUsername, addingTime) {
    companion object {
        fun fromResultSet(row: QueryRowSet): AnnouncementWithCategoryId? =
            try {
                AnnouncementWithCategoryId(
                    row[AnnouncementTable.id]!!,
                    row[CategoryTable.name]!!,
                    row[AnnouncementTable.categoryId]!!,
                    row[AnnouncementTable.city]!!,
                    row[AnnouncementTable.title]!!,
                    row[AnnouncementTable.description]!!,
                    row[AnnouncementTable.specialistUsername]!!,
                    row[AnnouncementTable.addingTime]!!.format(DateTimeFormatter.ofPattern("dd.MM.yyyy, HH:mm"))
                )
            } catch (_: NullPointerException) {
                null
            }
    }
}
