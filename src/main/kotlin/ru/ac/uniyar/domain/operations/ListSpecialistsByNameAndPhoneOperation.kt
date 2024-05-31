package ru.ac.uniyar.domain.operations

import org.ktorm.database.Database
import org.ktorm.dsl.and
import org.ktorm.dsl.asc
import org.ktorm.dsl.from
import org.ktorm.dsl.like
import org.ktorm.dsl.limit
import org.ktorm.dsl.mapNotNull
import org.ktorm.dsl.orderBy
import org.ktorm.dsl.select
import org.ktorm.dsl.where
import org.ktorm.support.mysql.toLowerCase
import ru.ac.uniyar.domain.database.entities.Specialist
import ru.ac.uniyar.domain.database.tables.SpecialistTable

class ListSpecialistsByNameAndPhoneOperation(
    private val database: Database,
) {
    companion object {
        const val SPECIALISTS_PER_PAGE = 5
    }
    fun list(page: Int, fullName: String, phone: String): List<Specialist> =
        database
            .from(SpecialistTable)
            .select()
            .where {
                (SpecialistTable.full_name.toLowerCase() like "%${fullName.lowercase()}%") and
                    (SpecialistTable.phone like "%$phone%")
            }
            .orderBy(SpecialistTable.adding_time.asc())
            .limit((page - 1) * SPECIALISTS_PER_PAGE, SPECIALISTS_PER_PAGE)
            .mapNotNull(Specialist::fromResultSet)
}
