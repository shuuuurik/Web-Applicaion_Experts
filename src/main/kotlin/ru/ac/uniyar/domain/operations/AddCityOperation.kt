package ru.ac.uniyar.domain.operations

import org.ktorm.database.Database
import org.ktorm.dsl.insert
import ru.ac.uniyar.domain.database.tables.CityTable

class AddCityOperation(
    private val database: Database,
) {
    fun add(name: String) {
        database.insert(CityTable) {
            set(it.name, name)
        }
    }
}
