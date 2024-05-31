package ru.ac.uniyar.domain.operations

import org.ktorm.database.Database
import org.ktorm.dsl.asc
import org.ktorm.dsl.from
import org.ktorm.dsl.mapNotNull
import org.ktorm.dsl.orderBy
import org.ktorm.dsl.select
import ru.ac.uniyar.domain.database.entities.Announcement
import ru.ac.uniyar.domain.database.tables.AnnouncementTable

class ListAnnouncementsOperation(
    private val database: Database,
) {
    fun list(): List<Announcement> =
        database
            .from(AnnouncementTable)
            .select()
            .orderBy(AnnouncementTable.adding_time.asc())
            .mapNotNull(Announcement::fromResultSet)
}
