package ru.ac.uniyar.domain.operations

import org.ktorm.database.Database
import org.ktorm.dsl.and
import org.ktorm.dsl.eq
import org.ktorm.dsl.from
import org.ktorm.dsl.leftJoin
import org.ktorm.dsl.mapNotNull
import org.ktorm.dsl.select
import org.ktorm.dsl.where
import ru.ac.uniyar.domain.database.entities.Category
import ru.ac.uniyar.domain.database.tables.CategoryTable
import ru.ac.uniyar.domain.database.tables.RequestTable

class ListCategoriesForSpecialistOperation(
    private val database: Database,
) {
    fun list(username: String): List<Category> =
        database
            .from(RequestTable)
            .leftJoin(CategoryTable, on = RequestTable.categoryId eq CategoryTable.id)
            .select()
            .where { (RequestTable.username eq username) and (RequestTable.status eq "Подтверждена") }
            .mapNotNull(Category::fromResultSet)
}
