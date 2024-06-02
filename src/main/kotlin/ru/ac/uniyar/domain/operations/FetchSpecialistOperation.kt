package ru.ac.uniyar.domain.operations

import org.ktorm.database.Database
import org.ktorm.dsl.and
import org.ktorm.dsl.eq
import org.ktorm.dsl.from
import org.ktorm.dsl.mapNotNull
import org.ktorm.dsl.select
import org.ktorm.dsl.where
import ru.ac.uniyar.domain.database.entities.User
import ru.ac.uniyar.domain.database.tables.UserTable

class FetchSpecialistOperation(
    private val database: Database,
) {

    @Suppress("MagicNumber")
    fun fetch(specialistUsername: String): User? =
        database
            .from(UserTable)
            .select()
            .where { (UserTable.username eq specialistUsername) and (UserTable.roleId eq 3) }
            .mapNotNull(User::fromResultSet)
            .firstOrNull()
}
