package ru.ac.uniyar.domain.operations

import org.ktorm.database.Database
import org.ktorm.dsl.eq
import org.ktorm.dsl.from
import org.ktorm.dsl.groupBy
import org.ktorm.dsl.leftJoin
import org.ktorm.dsl.mapNotNull
import org.ktorm.dsl.select
import org.ktorm.dsl.where
import ru.ac.uniyar.domain.database.entities.CategoryWithAnnouncementsNumber
import ru.ac.uniyar.domain.database.tables.AnnouncementTable
import ru.ac.uniyar.domain.database.tables.CategoryTable
import ru.ac.uniyar.domain.database.tables.announcementCount

class FetchCategoryOperation(
    private val database: Database,
) {
    fun fetch(categoryId: Int): CategoryWithAnnouncementsNumber? =
        database
            .from(CategoryTable)
            .leftJoin(AnnouncementTable, on = CategoryTable.id eq AnnouncementTable.categoryId)
            .select(CategoryTable.id, CategoryTable.name, announcementCount, CategoryTable.adding_time)
            .where { CategoryTable.id eq categoryId }
            .groupBy(CategoryTable.name)
            .mapNotNull(CategoryWithAnnouncementsNumber::fromResultSet)
            .firstOrNull()
}
