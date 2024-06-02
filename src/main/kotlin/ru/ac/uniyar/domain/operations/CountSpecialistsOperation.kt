package ru.ac.uniyar.domain.operations

import org.ktorm.database.Database
import org.ktorm.dsl.eq
import org.ktorm.dsl.from
import org.ktorm.dsl.select
import org.ktorm.dsl.where
import ru.ac.uniyar.domain.database.tables.UserTable

class CountSpecialistsOperation(
    private val database: Database,
) {
    @Suppress("MagicNumber")
    fun count(): Int =
        database
            .from(UserTable)
            .select()
            .where { UserTable.roleId eq 3 }
            .totalRecords
}
