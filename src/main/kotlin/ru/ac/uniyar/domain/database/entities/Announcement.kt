package ru.ac.uniyar.domain.database.entities

import org.ktorm.dsl.QueryRowSet
import ru.ac.uniyar.domain.database.tables.AnnouncementTable
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

data class Announcement(
    val id: Int,
    val category: String,
    val title: String,
    val city: String,
    val description: String,
    val specialistId: Int,
    val addingTime: String = LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd.MM.yyyy, HH:mm"))
) {
    companion object {
        fun fromResultSet(row: QueryRowSet): Announcement? =
            try {
                Announcement(
                    row[AnnouncementTable.id]!!,
                    row[AnnouncementTable.category]!!,
                    row[AnnouncementTable.title]!!,
                    row[AnnouncementTable.city]!!,
                    row[AnnouncementTable.description]!!,
                    row[AnnouncementTable.specialistId]!!,
                    row[AnnouncementTable.adding_time]!!.format(DateTimeFormatter.ofPattern("dd.MM.yyyy, HH:mm"))
                )
            } catch (npe: NullPointerException) {
                null
            }
    }
}
