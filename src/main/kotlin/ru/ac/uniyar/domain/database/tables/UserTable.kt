package ru.ac.uniyar.domain.database.tables

import org.ktorm.schema.Table
import org.ktorm.schema.datetime
import org.ktorm.schema.int
import org.ktorm.schema.text
import org.ktorm.schema.varchar

object UserTable : Table<Nothing>("USERS") {
    val username = varchar("USERNAME").primaryKey()
    val password = varchar("PASSWORD")
    val fullName = varchar("FULL_NAME")
    val phone = varchar("PHONE")
    val city = varchar("CITY_NAME")
    val roleId = int("ROLE_ID")
    val education = text("EDUCATION")
    val addingTime = datetime("ADDING_TIME")
}
