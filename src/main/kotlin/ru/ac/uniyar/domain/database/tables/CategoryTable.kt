package ru.ac.uniyar.domain.database.tables

import org.ktorm.schema.Table
import org.ktorm.schema.datetime
import org.ktorm.schema.int
import org.ktorm.schema.varchar

object CategoryTable : Table<Nothing>("CATEGORY") {
    val id = int("ID").primaryKey()
    val name = varchar("NAME")
    val adding_time = datetime("ADDING_TIME")
}
