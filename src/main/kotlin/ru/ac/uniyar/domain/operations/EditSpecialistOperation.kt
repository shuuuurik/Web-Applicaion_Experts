package ru.ac.uniyar.domain.operations

import org.ktorm.database.Database
import org.ktorm.dsl.eq
import org.ktorm.dsl.update
import ru.ac.uniyar.domain.database.tables.UserTable

class EditSpecialistOperation(
    private val database: Database,
) {
    fun edit(username: String, education: String) =
        database.update(UserTable) {
            set(it.education, education)
            where {
                it.username eq username
            }
        }
}
