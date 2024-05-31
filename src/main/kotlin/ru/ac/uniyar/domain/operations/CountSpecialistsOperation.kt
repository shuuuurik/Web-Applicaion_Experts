package ru.ac.uniyar.domain.operations

import org.ktorm.database.Database
import org.ktorm.dsl.from
import org.ktorm.dsl.select
import ru.ac.uniyar.domain.database.tables.SpecialistTable

class CountSpecialistsOperation(
    private val database: Database,
) {
    fun count(): Int =
        database
            .from(SpecialistTable)
            .select()
            .totalRecords
}
