package ru.ac.uniyar.domain.database.tables

import org.ktorm.schema.Table
import org.ktorm.schema.datetime
import org.ktorm.schema.varchar

object CategoryTable : Table<Nothing>("CATEGORY") {
    val name = varchar("NAME").primaryKey()
    val adding_time = datetime("ADDING_TIME")
}

/*interface Categoryi : Entity<Categoryi> {
    var name: String
    var announcementsNumber: Int
    var addingTime: LocalDateTime
}*/
