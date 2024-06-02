package ru.ac.uniyar.domain.database.entities

import org.ktorm.dsl.QueryRowSet
import ru.ac.uniyar.domain.database.tables.UserTable
import java.time.format.DateTimeFormatter

data class User(
    val username: String,
    val fullName: String,
    val phone: String,
    val currentCity: String,
    val roleId: Int,
    val education: String?,
    val addingTime: String?
) {
    companion object {
        fun fromResultSet(row: QueryRowSet): User? =
            try {
                User(
                    row[UserTable.username]!!,
                    row[UserTable.fullName]!!,
                    row[UserTable.phone]!!,
                    row[UserTable.city]!!,
                    row[UserTable.roleId]!!,
                    row[UserTable.education],
                    row[UserTable.addingTime]?.format(DateTimeFormatter.ofPattern("dd.MM.yy, HH:mm"))
                )
            } catch (_: NullPointerException) {
                null
            }
    }
}
