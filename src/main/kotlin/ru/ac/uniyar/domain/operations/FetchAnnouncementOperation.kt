package ru.ac.uniyar.domain.operations

import org.ktorm.database.Database
import org.ktorm.dsl.eq
import org.ktorm.dsl.from
import org.ktorm.dsl.mapNotNull
import org.ktorm.dsl.select
import org.ktorm.dsl.where
import ru.ac.uniyar.domain.database.entities.Announcement
import ru.ac.uniyar.domain.database.tables.AnnouncementTable

class FetchAnnouncementOperation(
    private val database: Database,
) {
    fun fetch(announcementId: Int): Announcement? =
        database
            .from(AnnouncementTable)
            .select()
            .where { AnnouncementTable.id eq announcementId }
            .mapNotNull(Announcement::fromResultSet)
            .firstOrNull()
}
