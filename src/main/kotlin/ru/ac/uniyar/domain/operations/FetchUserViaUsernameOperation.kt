package ru.ac.uniyar.domain.operations

import org.ktorm.database.Database
import org.ktorm.dsl.eq
import org.ktorm.dsl.from
import org.ktorm.dsl.mapNotNull
import org.ktorm.dsl.select
import org.ktorm.dsl.where
import ru.ac.uniyar.domain.database.entities.User
import ru.ac.uniyar.domain.database.tables.UserTable

class FetchUserViaUsernameOperation(
    private val database: Database,
) {
    fun fetch(username: String): User? =
        database
            .from(UserTable)
            .select()
            .where { UserTable.username eq username }
            .mapNotNull(User::fromResultSet)
            .firstOrNull()
}
