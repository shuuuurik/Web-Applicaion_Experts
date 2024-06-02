package ru.ac.uniyar.domain.database.entities

import org.ktorm.dsl.QueryRowSet
import ru.ac.uniyar.domain.database.tables.CategoryTable
import ru.ac.uniyar.domain.database.tables.announcementCount
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class CategoryWithAnnouncementsNumber(
    override val id: Int,
    override val name: String,
    val announcementsNumber: Int,
    override val addingTime: String = LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd.MM.yy, HH:mm"))
) : Category(id, name, addingTime) {
    companion object {
        fun fromResultSet(row: QueryRowSet): CategoryWithAnnouncementsNumber? =
            try {
                CategoryWithAnnouncementsNumber(
                    row[CategoryTable.id]!!,
                    row[CategoryTable.name]!!,
                    row[announcementCount]!!,
                    row[CategoryTable.adding_time]!!.format(DateTimeFormatter.ofPattern("dd.MM.yy, HH:mm"))
                )
            } catch (_: NullPointerException) {
                null
            }
    }
}
