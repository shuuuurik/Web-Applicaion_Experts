package ru.ac.uniyar.domain.operations

import org.ktorm.database.Database
import org.ktorm.dsl.eq
import org.ktorm.dsl.from
import org.ktorm.dsl.mapNotNull
import org.ktorm.dsl.select
import org.ktorm.dsl.where
import ru.ac.uniyar.domain.database.entities.RolePermissions
import ru.ac.uniyar.domain.database.tables.RoleTable

class FetchRoleViaIdOperation(
    private val database: Database,
) {
    fun fetch(roleId: Int): RolePermissions? =
        database
            .from(RoleTable)
            .select()
            .where { RoleTable.id eq roleId }
            .mapNotNull(RolePermissions::fromResultSet)
            .firstOrNull()
}
