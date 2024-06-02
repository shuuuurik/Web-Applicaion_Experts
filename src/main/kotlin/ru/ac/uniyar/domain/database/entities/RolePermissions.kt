package ru.ac.uniyar.domain.database.entities

import org.ktorm.dsl.QueryRowSet
import ru.ac.uniyar.domain.database.tables.RoleTable

data class RolePermissions(
    val id: Int,
    val name: String,
    val listSpecialists: Boolean = false,
    val showSpecialist: Boolean = false,
    val editUser: Boolean = false,
    val listRequests: Boolean = false,
    val showRequest: Boolean = false,
    val addRequest: Boolean = false,
    val editRequest: Boolean = false,
    val addAnnouncement: Boolean = false,
    val editAnnouncement: Boolean = false,
    val addCity: Boolean = false,
    val addCategory: Boolean = false
) {
    companion object {
        val ANONYMOUS_ROLE = RolePermissions(
            id = 0,
            name = "Гость",
        )

        fun fromResultSet(row: QueryRowSet): RolePermissions? =
            try {
                RolePermissions(
                    row[RoleTable.id]!!,
                    row[RoleTable.name]!!,
                    row[RoleTable.listSpecialists]!!,
                    row[RoleTable.showSpecialist]!!,
                    row[RoleTable.editUser]!!,
                    row[RoleTable.listRequests]!!,
                    row[RoleTable.showRequest]!!,
                    row[RoleTable.addRequest]!!,
                    row[RoleTable.editRequest]!!,
                    row[RoleTable.addAnnouncement]!!,
                    row[RoleTable.editAnnouncement]!!,
                    row[RoleTable.addCity]!!,
                    row[RoleTable.addCategory]!!,
                )
            } catch (_: NullPointerException) {
                null
            }
    }
}
