package ru.ac.uniyar.domain.operations

import org.ktorm.database.Database
import org.ktorm.dsl.eq
import org.ktorm.dsl.from
import org.ktorm.dsl.mapNotNull
import org.ktorm.dsl.select
import org.ktorm.dsl.where
import ru.ac.uniyar.domain.database.entities.City
import ru.ac.uniyar.domain.database.tables.CityTable

class FetchCityOperation(
    private val database: Database,
) {
    fun fetch(cityName: String): City? =
        database
            .from(CityTable)
            .select()
            .where { CityTable.name eq cityName }
            .mapNotNull(City::fromResultSet)
            .firstOrNull()
}
