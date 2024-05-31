package ru.ac.uniyar.domain.operations

import org.ktorm.database.Database
import org.ktorm.dsl.asc
import org.ktorm.dsl.from
import org.ktorm.dsl.mapNotNull
import org.ktorm.dsl.orderBy
import org.ktorm.dsl.select
import ru.ac.uniyar.domain.database.entities.Category
import ru.ac.uniyar.domain.database.tables.CategoryTable

class ListCategoriesOperation(
    private val database: Database,
) {
    fun list(): List<Category> =
        database
            .from(CategoryTable)
            .select()
            .orderBy(CategoryTable.adding_time.asc())
            .mapNotNull(Category::fromResultSet)
}
