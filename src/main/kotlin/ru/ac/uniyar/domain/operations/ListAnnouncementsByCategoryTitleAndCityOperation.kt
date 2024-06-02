package ru.ac.uniyar.domain.operations

import org.ktorm.database.Database
import org.ktorm.dsl.and
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
import ru.ac.uniyar.domain.database.tables.CategoryTable

class ListAnnouncementsByCategoryTitleAndCityOperation(
    private val database: Database,
) {
    companion object {
        const val ANNOUNCEMENTS_PER_PAGE = 5
    }
    fun list(page: Int, category: String, title: String, city: String): List<Announcement> =
        database
            .from(AnnouncementTable)
            .leftJoin(CategoryTable, on = AnnouncementTable.categoryId eq CategoryTable.id)
            .select()
            .where {
                (CategoryTable.name like "%$category%") and
                    (AnnouncementTable.title.toLowerCase() like "%${title.lowercase()}%") and
                    (AnnouncementTable.city like "%$city%")
            }
            .orderBy(AnnouncementTable.addingTime.asc())
            .limit((page - 1) * ANNOUNCEMENTS_PER_PAGE, ANNOUNCEMENTS_PER_PAGE)
            .mapNotNull(Announcement::fromResultSet)
}
