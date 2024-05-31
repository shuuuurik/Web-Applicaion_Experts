package ru.ac.uniyar.domain.operations

import org.ktorm.database.Database
import org.ktorm.dsl.from
import org.ktorm.dsl.select
import ru.ac.uniyar.domain.database.tables.AnnouncementTable

class CountAnnouncementsOperation(
    private val database: Database,
) {
    fun count(): Int =
        database
            .from(AnnouncementTable)
            .select()
            .totalRecords
}
