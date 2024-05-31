package ru.ac.uniyar.domain.database.tables

import org.ktorm.schema.Table
import org.ktorm.schema.datetime
import org.ktorm.schema.int
import org.ktorm.schema.text
import org.ktorm.schema.varchar

object SpecialistTable : Table<Nothing>("SPECIALIST") {
    val id = int("ID").primaryKey()
    val adding_time = datetime("ADDING_TIME")
    val full_name = varchar("FULL_NAME")
    val education = text("EDUCATION")
    val phone = varchar("PHONE")
}
