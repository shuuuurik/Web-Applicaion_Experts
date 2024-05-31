package ru.ac.uniyar.domain.operations

import org.ktorm.database.Database
import org.ktorm.dsl.asc
import org.ktorm.dsl.eq
import org.ktorm.dsl.from
import org.ktorm.dsl.groupBy
import org.ktorm.dsl.leftJoin
import org.ktorm.dsl.limit
import org.ktorm.dsl.mapNotNull
import org.ktorm.dsl.orderBy
import org.ktorm.dsl.select
import org.ktorm.dsl.where
import ru.ac.uniyar.domain.database.entities.CategoryWithAnnouncementsNumber
import ru.ac.uniyar.domain.database.tables.AnnouncementTable
import ru.ac.uniyar.domain.database.tables.CategoryTable
import ru.ac.uniyar.domain.database.tables.CityTable
import ru.ac.uniyar.domain.database.tables.announcementCount

class ListCategoriesByCityOperation(
    private val database: Database,
) {
    companion object {
        const val MAX_ROWS_NUMBER = 5
    }
    fun list(cityName: String): List<CategoryWithAnnouncementsNumber> =
        database
            .from(AnnouncementTable)
            .leftJoin(CityTable, on = AnnouncementTable.city eq CityTable.name)
            .leftJoin(CategoryTable, on = AnnouncementTable.category eq CategoryTable.name)
            .select(CategoryTable.name, announcementCount, CategoryTable.adding_time)
            .where { CityTable.name eq cityName }
            .groupBy(CategoryTable.name)
            .orderBy(CategoryTable.adding_time.asc())
            .limit(MAX_ROWS_NUMBER)
            .mapNotNull(CategoryWithAnnouncementsNumber::fromResultSet)
}
