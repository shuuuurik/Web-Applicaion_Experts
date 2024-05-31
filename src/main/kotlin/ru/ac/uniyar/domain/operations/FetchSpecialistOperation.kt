package ru.ac.uniyar.domain.operations

import org.ktorm.database.Database
import org.ktorm.dsl.eq
import org.ktorm.dsl.from
import org.ktorm.dsl.mapNotNull
import org.ktorm.dsl.select
import org.ktorm.dsl.where
import ru.ac.uniyar.domain.database.entities.Specialist
import ru.ac.uniyar.domain.database.tables.SpecialistTable

class FetchSpecialistOperation(
    private val database: Database,
) {
    fun fetch(specialistId: Int): Specialist? =
        database
            .from(SpecialistTable)
            .select()
            .where { SpecialistTable.id eq specialistId }
            .mapNotNull(Specialist::fromResultSet)
            .firstOrNull()
}
