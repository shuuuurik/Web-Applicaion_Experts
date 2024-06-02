package ru.ac.uniyar.domain.operations

import org.ktorm.database.Database
import org.ktorm.dsl.asc
import org.ktorm.dsl.eq
import org.ktorm.dsl.from
import org.ktorm.dsl.groupBy
import org.ktorm.dsl.leftJoin
import org.ktorm.dsl.like
import org.ktorm.dsl.limit
import org.ktorm.dsl.mapNotNull
import org.ktorm.dsl.orderBy
import org.ktorm.dsl.select
import org.ktorm.dsl.where
import org.ktorm.support.mysql.toLowerCase
import ru.ac.uniyar.domain.database.entities.CategoryWithAnnouncementsNumber
import ru.ac.uniyar.domain.database.tables.AnnouncementTable
import ru.ac.uniyar.domain.database.tables.CategoryTable
import ru.ac.uniyar.domain.database.tables.announcementCount

class ListCategoriesByNameOperation(
    private val database: Database,
) {
    companion object {
        const val CATEGORIES_PER_PAGE = 5
    }
    fun list(page: Int, categoryName: String): List<CategoryWithAnnouncementsNumber> =
        database
            .from(CategoryTable)
            .leftJoin(AnnouncementTable, on = CategoryTable.id eq AnnouncementTable.categoryId)
            .select(CategoryTable.id, CategoryTable.name, announcementCount, CategoryTable.adding_time)
            .where { (CategoryTable.name.toLowerCase() like "%${categoryName.lowercase()}%") }
            .groupBy(CategoryTable.id)
            .orderBy(CategoryTable.adding_time.asc())
            .limit((page - 1) * CATEGORIES_PER_PAGE, CATEGORIES_PER_PAGE)
            .mapNotNull(CategoryWithAnnouncementsNumber::fromResultSet)
}
