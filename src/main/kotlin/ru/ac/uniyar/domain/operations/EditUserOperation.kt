package ru.ac.uniyar.domain.operations

import org.ktorm.database.Database
import org.ktorm.dsl.eq
import org.ktorm.dsl.update
import ru.ac.uniyar.domain.database.tables.UserTable

class EditUserOperation(
    private val database: Database,
) {
    fun edit(username: String, fullName: String, phone: String, city: String) =
        database.update(UserTable) {
            set(it.fullName, fullName)
            set(it.phone, phone)
            set(it.city, city)
            where {
                it.username eq username
            }
        }
}
