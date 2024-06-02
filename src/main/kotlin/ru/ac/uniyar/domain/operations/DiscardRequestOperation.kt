package ru.ac.uniyar.domain.operations

import org.ktorm.database.Database
import org.ktorm.dsl.eq
import org.ktorm.dsl.update
import ru.ac.uniyar.domain.database.tables.RequestTable

class DiscardRequestOperation(
    private val database: Database,
) {
    fun discard(requestId: Int, reason: String) =
        database.update(RequestTable) {
            set(it.status, "Отклонена")
            set(it.reason, reason)
            where {
                it.id eq requestId
            }
        }
}
