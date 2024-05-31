package ru.ac.uniyar.domain.database.entities

import org.ktorm.dsl.QueryRowSet
import ru.ac.uniyar.domain.database.tables.CategoryTable
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

open class Category(
    open val name: String,
    open val addingTime: String = LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd.MM.yy, HH:mm"))
) {
    companion object {
        fun fromResultSet(row: QueryRowSet): Category? =
            try {
                Category(
                    row[CategoryTable.name]!!,
                    row[CategoryTable.adding_time]!!.format(DateTimeFormatter.ofPattern("dd.MM.yy, HH:mm"))
                )
            } catch (npe: NullPointerException) {
                null
            }
    }
}
