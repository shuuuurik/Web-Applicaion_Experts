package ru.ac.uniyar.domain.database.entities

import org.ktorm.dsl.QueryRowSet
import ru.ac.uniyar.domain.database.tables.CityTable

data class City(
    val name: String
) {
    companion object {
        fun fromResultSet(row: QueryRowSet): City? =
            try {
                City(
                    row[CityTable.name]!!,
                )
            } catch (_: NullPointerException) {
                null
            }
    }
}
