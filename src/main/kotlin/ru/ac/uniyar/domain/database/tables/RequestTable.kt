package ru.ac.uniyar.domain.database.tables

import org.ktorm.schema.Table
import org.ktorm.schema.datetime
import org.ktorm.schema.int
import org.ktorm.schema.text
import org.ktorm.schema.varchar

object RequestTable : Table<Nothing>("REQUEST") {
    val id = int("ID").primaryKey()
    val username = varchar("USERS_USERNAME")
    val categoryId = int("CATEGORY_ID")
    val education = text("EDUCATION")
    val experience = text("EXPERIENCE")
    val status = varchar("STATUS")
    val reason = text("REASON")
    val addingTime = datetime("ADDING_TIME")
}
