package ru.ac.uniyar.domain.database.entities

import org.ktorm.dsl.QueryRowSet
import ru.ac.uniyar.domain.database.tables.CategoryTable
import ru.ac.uniyar.domain.database.tables.RequestTable
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

data class Request(
    val id: Int,
    val username: String,
    val category: String,
    val education: String,
    val experience: String,
    val status: String,
    val reason: String?,
    val addingTime: String = LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd.MM.yyyy, HH:mm"))
) {
    companion object {
        fun fromResultSet(row: QueryRowSet): Request? =
            try {
                Request(
                    row[RequestTable.id]!!,
                    row[RequestTable.username]!!,
                    row[CategoryTable.name]!!,
                    row[RequestTable.education]!!,
                    row[RequestTable.experience]!!,
                    row[RequestTable.status]!!,
                    row[RequestTable.reason],
                    row[RequestTable.addingTime]!!.format(DateTimeFormatter.ofPattern("dd.MM.yyyy, HH:mm"))
                )
            } catch (_: NullPointerException) {
                null
            }
    }
}
