package ru.ac.uniyar.domain.operations

import org.ktorm.database.Database
import org.ktorm.dsl.insert
import ru.ac.uniyar.domain.database.tables.SpecialistTable
import java.time.LocalDateTime

class AddSpecialistOperation(
    private val database: Database,
) {
    fun add(
        fullName: String,
        education: String,
        phone: String
    ) {
        database.insert(SpecialistTable) {
            set(it.full_name, fullName)
            set(it.education, education)
            set(it.phone, phone)
            set(it.adding_time, LocalDateTime.now())
        }
    }
}
