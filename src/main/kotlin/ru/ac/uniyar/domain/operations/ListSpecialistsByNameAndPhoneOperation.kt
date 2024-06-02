package ru.ac.uniyar.domain.operations

import org.ktorm.database.Database
import org.ktorm.dsl.and
import org.ktorm.dsl.asc
import org.ktorm.dsl.eq
import org.ktorm.dsl.from
import org.ktorm.dsl.like
import org.ktorm.dsl.limit
import org.ktorm.dsl.mapNotNull
import org.ktorm.dsl.orderBy
import org.ktorm.dsl.select
import org.ktorm.dsl.where
import org.ktorm.support.mysql.toLowerCase
import ru.ac.uniyar.domain.database.entities.User
import ru.ac.uniyar.domain.database.tables.UserTable

class ListSpecialistsByNameAndPhoneOperation(
    private val database: Database,
) {
    companion object {
        const val SPECIALISTS_PER_PAGE = 5
    }

    @Suppress("MagicNumber")
    fun list(page: Int, fullName: String, phone: String): List<User> =
        database
            .from(UserTable)
            .select()
            .where {
                (UserTable.fullName.toLowerCase() like "%${fullName.lowercase()}%") and
                    (UserTable.phone like "%$phone%") and (UserTable.roleId eq 3)
            }
            .orderBy(UserTable.addingTime.asc())
            .limit((page - 1) * SPECIALISTS_PER_PAGE, SPECIALISTS_PER_PAGE)
            .mapNotNull(User::fromResultSet)
}
