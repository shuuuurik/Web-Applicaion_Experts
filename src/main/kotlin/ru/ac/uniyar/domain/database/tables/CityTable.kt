package ru.ac.uniyar.domain.database.tables

import org.ktorm.schema.Table
import org.ktorm.schema.varchar

object CityTable : Table<Nothing>("CITY") {
    val name = varchar("NAME").primaryKey()
}
