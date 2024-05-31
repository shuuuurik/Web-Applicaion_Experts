package ru.ac.uniyar.domain.database.tables

import org.ktorm.schema.Table
import org.ktorm.schema.datetime
import org.ktorm.schema.int
import org.ktorm.schema.text
import org.ktorm.schema.varchar

object AnnouncementTable : Table<Nothing>("ANNOUNCEMENT") {
    val id = int("ID").primaryKey()
    val adding_time = datetime("ADDING_TIME")
    val category = varchar("CATEGORY_NAME")
    val title = varchar("TITLE")
    val city = varchar("CITY_NAME")
    val description = text("DESCRIPTION")
    val specialistId = int("SPECIALIST_ID")
}
