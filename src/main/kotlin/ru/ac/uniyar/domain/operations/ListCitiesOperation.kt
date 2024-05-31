package ru.ac.uniyar.domain.operations

import org.ktorm.database.Database
import org.ktorm.dsl.from
import org.ktorm.dsl.mapNotNull
import org.ktorm.dsl.select
import ru.ac.uniyar.domain.database.entities.City
import ru.ac.uniyar.domain.database.tables.CityTable

class ListCitiesOperation(
    private val database: Database,
) {
    fun list(): List<City> =
        database
            .from(CityTable)
            .select()
            .mapNotNull(City::fromResultSet)
}
