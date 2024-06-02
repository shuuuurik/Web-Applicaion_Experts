package ru.ac.uniyar.domain.operations

import org.ktorm.database.Database
import org.ktorm.dsl.asc
import org.ktorm.dsl.from
import org.ktorm.dsl.mapNotNull
import org.ktorm.dsl.orderBy
import org.ktorm.dsl.select
import ru.ac.uniyar.domain.database.entities.User
import ru.ac.uniyar.domain.database.tables.UserTable

class ListUsersOperation(
    private val database: Database,
) {
    fun list(): List<User> =
        database
            .from(UserTable)
            .select()
            .orderBy(UserTable.roleId.asc())
            .mapNotNull(User::fromResultSet)
}
