package ru.ac.uniyar.domain.operations

import org.ktorm.database.Database
import org.ktorm.dsl.eq
import org.ktorm.dsl.update
import ru.ac.uniyar.domain.database.tables.RequestTable

class AcceptRequestOperation(
    private val database: Database,
) {
    fun accept(requestId: Int) =
        database.update(RequestTable) {
            set(it.status, "Подтверждена")
            where {
                it.id eq requestId
            }
        }
}
