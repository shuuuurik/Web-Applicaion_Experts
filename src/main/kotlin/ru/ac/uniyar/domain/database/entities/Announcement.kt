package ru.ac.uniyar.domain.database.entities

import org.ktorm.dsl.QueryRowSet
import ru.ac.uniyar.domain.database.tables.AnnouncementTable
import ru.ac.uniyar.domain.database.tables.CategoryTable
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@Suppress("LongParameterList")
open class Announcement(
    open val id: Int,
    open val category: String,
    open val city: String,
    open val title: String,
    open val description: String,
    open val specialistUsername: String,
    open val addingTime: String = LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd.MM.yyyy, HH:mm"))
) {
    companion object {
        fun fromResultSet(row: QueryRowSet): Announcement? =
            try {
                Announcement(
                    row[AnnouncementTable.id]!!,
                    row[CategoryTable.name]!!,
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
