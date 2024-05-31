package ru.ac.uniyar.domain.database.entities

import org.ktorm.dsl.QueryRowSet
import ru.ac.uniyar.domain.database.tables.SpecialistTable
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

data class Specialist(
    val id: Int,
    val fullName: String,
    val education: String,
    val phone: String,
    val addingTime: String = LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd.MM.yy, HH:mm"))
) {
    companion object {
        fun fromResultSet(row: QueryRowSet): Specialist? =
            try {
                Specialist(
                    row[SpecialistTable.id]!!,
                    row[SpecialistTable.full_name]!!,
                    row[SpecialistTable.education]!!,
                    row[SpecialistTable.phone]!!,
                    row[SpecialistTable.adding_time]!!.format(DateTimeFormatter.ofPattern("dd.MM.yy, HH:mm"))
                )
            } catch (npe: NullPointerException) {
                null
            }
    }
}
