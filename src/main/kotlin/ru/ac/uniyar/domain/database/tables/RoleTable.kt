package ru.ac.uniyar.domain.database.tables

import org.ktorm.schema.Table
import org.ktorm.schema.boolean
import org.ktorm.schema.int
import org.ktorm.schema.varchar

object RoleTable : Table<Nothing>("ROLE") {
    val id = int("ID").primaryKey()
    val name = varchar("NAME")
    val listSpecialists = boolean("LIST_SPECIALISTS")
    val showSpecialist = boolean("SHOW_SPECIALIST")
    val editUser = boolean("EDIT_USER")
    val listRequests = boolean("LIST_REQUESTS")
    val showRequest = boolean("SHOW_REQUEST")
    val addRequest = boolean("ADD_REQUEST")
    val editRequest = boolean("EDIT_REQUEST")
    val addAnnouncement = boolean("ADD_ANNOUNCEMENT")
    val editAnnouncement = boolean("EDIT_ANNOUNCEMENT")
    val addCity = boolean("ADD_CITY")
    val addCategory = boolean("ADD_CATEGORY")
}
