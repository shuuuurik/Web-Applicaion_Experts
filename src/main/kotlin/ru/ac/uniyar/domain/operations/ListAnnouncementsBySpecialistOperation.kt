package ru.ac.uniyar.domain.operations

import org.ktorm.database.Database
import org.ktorm.dsl.asc
import org.ktorm.dsl.eq
import org.ktorm.dsl.from
import org.ktorm.dsl.leftJoin
import org.ktorm.dsl.limit
import org.ktorm.dsl.mapNotNull
import org.ktorm.dsl.orderBy
import org.ktorm.dsl.select
import org.ktorm.dsl.where
import ru.ac.uniyar.domain.database.entities.Announcement
import ru.ac.uniyar.domain.database.tables.AnnouncementTable
import ru.ac.uniyar.domain.database.tables.CategoryTable

class ListAnnouncementsBySpecialistOperation(
    private val database: Database,
) {
    companion object {
        const val ANNOUNCEMENTS_PER_PAGE = 5
    }
    fun list(page: Int, specialistUsername: String): List<Announcement> =
        database
            .from(AnnouncementTable)
            .leftJoin(CategoryTable, on = AnnouncementTable.categoryId eq CategoryTable.id)
            .select()
            .where { AnnouncementTable.specialistUsername eq specialistUsername }
            .orderBy(AnnouncementTable.addingTime.asc())
            .limit((page - 1) * ANNOUNCEMENTS_PER_PAGE, ANNOUNCEMENTS_PER_PAGE)
            .mapNotNull(Announcement::fromResultSet)
}
