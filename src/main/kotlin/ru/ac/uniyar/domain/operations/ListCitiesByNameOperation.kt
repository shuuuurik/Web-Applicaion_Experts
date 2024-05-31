package ru.ac.uniyar.domain.operations

import org.ktorm.database.Database
import org.ktorm.dsl.from
import org.ktorm.dsl.like
import org.ktorm.dsl.limit
import org.ktorm.dsl.mapNotNull
import org.ktorm.dsl.select
import org.ktorm.dsl.where
import org.ktorm.support.mysql.toLowerCase
import ru.ac.uniyar.domain.database.entities.City
import ru.ac.uniyar.domain.database.tables.CityTable

class ListCitiesByNameOperation(
    private val database: Database,
) {
    companion object {
        const val CITIES_PER_PAGE = 5
    }
    fun list(page: Int, name: String): List<City> =
        database
            .from(CityTable)
            .select()
            .where { (CityTable.name.toLowerCase() like "%${name.lowercase()}%") }
            .limit((page - 1) * CITIES_PER_PAGE, CITIES_PER_PAGE)
            .mapNotNull(City::fromResultSet)
}
