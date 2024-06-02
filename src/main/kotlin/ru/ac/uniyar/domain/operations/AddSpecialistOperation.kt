package ru.ac.uniyar.domain.operations

import org.ktorm.database.Database
import org.ktorm.dsl.eq
import org.ktorm.dsl.update
import ru.ac.uniyar.domain.database.tables.UserTable
import java.time.LocalDateTime

class AddSpecialistOperation(
    private val database: Database,
) {
    @Suppress("MagicNumber")
    fun add(username: String, education: String) {
        database.update(UserTable) {
            set(it.education, education)
            set(it.addingTime, LocalDateTime.now())
            set(it.roleId, 3)
            where {
                it.username eq username
            }
        }
    }
}
