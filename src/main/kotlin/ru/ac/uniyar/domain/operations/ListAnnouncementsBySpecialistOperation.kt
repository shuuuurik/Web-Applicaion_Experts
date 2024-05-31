package ru.ac.uniyar.domain.operations

import org.ktorm.database.Database
import org.ktorm.dsl.asc
import org.ktorm.dsl.eq
import org.ktorm.dsl.from
import org.ktorm.dsl.leftJoin
import org.ktorm.dsl.like
import org.ktorm.dsl.limit
import org.ktorm.dsl.mapNotNull
import org.ktorm.dsl.orderBy
import org.ktorm.dsl.select
import org.ktorm.dsl.where
import org.ktorm.support.mysql.toLowerCase
import ru.ac.uniyar.domain.database.entities.Announcement
import ru.ac.uniyar.domain.database.tables.AnnouncementTable
import ru.ac.uniyar.domain.database.tables.SpecialistTable

class ListAnnouncementsBySpecialistOperation(
    private val database: Database,
) {
    companion object {
        const val ANNOUNCEMENTS_PER_PAGE = 5
    }
    fun list(page: Int, specialist: String): List<Announcement> =
        database
            .from(AnnouncementTable)
            .leftJoin(SpecialistTable, on = AnnouncementTable.specialistId eq SpecialistTable.id)
            .select(
                AnnouncementTable.id, AnnouncementTable.category, AnnouncementTable.title, AnnouncementTable.city,
                AnnouncementTable.description, AnnouncementTable.specialistId, AnnouncementTable.adding_time
            )
            .where { (SpecialistTable.full_name.toLowerCase() like "%${specialist.lowercase()}%") }
            .orderBy(AnnouncementTable.adding_time.asc())
            .limit((page - 1) * ANNOUNCEMENTS_PER_PAGE, ANNOUNCEMENTS_PER_PAGE)
            .mapNotNull(Announcement::fromResultSet)
}
