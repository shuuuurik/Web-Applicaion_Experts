package ru.ac.uniyar.domain.operations

import org.ktorm.database.Database
import org.ktorm.dsl.eq
import org.ktorm.dsl.from
import org.ktorm.dsl.leftJoin
import org.ktorm.dsl.mapNotNull
import org.ktorm.dsl.select
import org.ktorm.dsl.where
import ru.ac.uniyar.domain.database.entities.AnnouncementWithCategoryId
import ru.ac.uniyar.domain.database.tables.AnnouncementTable
import ru.ac.uniyar.domain.database.tables.CategoryTable

class FetchAnnouncementOperation(
    private val database: Database,
) {
    fun fetch(announcementId: Int): AnnouncementWithCategoryId? =
        database
            .from(AnnouncementTable)
            .leftJoin(CategoryTable, on = AnnouncementTable.categoryId eq CategoryTable.id)
            .select()
            .where { AnnouncementTable.id eq announcementId }
            .mapNotNull(AnnouncementWithCategoryId::fromResultSet)
            .firstOrNull()
}
