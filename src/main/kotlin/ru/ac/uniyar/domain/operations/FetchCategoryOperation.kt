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
    fun fetch(categoryName: String): CategoryWithAnnouncementsNumber? =
        database
            .from(CategoryTable)
            .leftJoin(AnnouncementTable, on = CategoryTable.name eq AnnouncementTable.category)
            .select(CategoryTable.name, announcementCount, CategoryTable.adding_time)
            .where { CategoryTable.name eq categoryName }
            .groupBy(CategoryTable.name)
            .mapNotNull(CategoryWithAnnouncementsNumber::fromResultSet)
            .firstOrNull()
}
