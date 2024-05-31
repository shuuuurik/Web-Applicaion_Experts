package ru.ac.uniyar.domain.operations

import org.ktorm.database.Database
import org.ktorm.dsl.asc
import org.ktorm.dsl.from
import org.ktorm.dsl.mapNotNull
import org.ktorm.dsl.orderBy
import org.ktorm.dsl.select
import ru.ac.uniyar.domain.database.entities.Specialist
import ru.ac.uniyar.domain.database.tables.SpecialistTable

class ListSpecialistsOperation(
    private val database: Database,
) {
    fun list(): List<Specialist> =
        database
            .from(SpecialistTable)
            .select()
            .orderBy(SpecialistTable.adding_time.asc())
            .mapNotNull(Specialist::fromResultSet)
}
