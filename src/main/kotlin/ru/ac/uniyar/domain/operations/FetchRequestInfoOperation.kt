package ru.ac.uniyar.domain.operations

import org.ktorm.database.Database
import org.ktorm.dsl.eq
import org.ktorm.dsl.from
import org.ktorm.dsl.leftJoin
import org.ktorm.dsl.mapNotNull
import org.ktorm.dsl.select
import org.ktorm.dsl.where
import ru.ac.uniyar.domain.database.entities.RequestInfo
import ru.ac.uniyar.domain.database.entities.User
import ru.ac.uniyar.domain.database.tables.CategoryTable
import ru.ac.uniyar.domain.database.tables.RequestTable
import ru.ac.uniyar.domain.database.tables.UserTable

class FetchRequestInfoOperation(
    private val database: Database,
) {
    fun fetch(user: User?, requestId: Int): RequestInfo =
        database
            .from(RequestTable)
            .leftJoin(UserTable, on = RequestTable.username eq UserTable.username)
            .leftJoin(CategoryTable, on = RequestTable.categoryId eq CategoryTable.id)
            .select()
            .where { RequestTable.id eq requestId }
            .mapNotNull { row -> RequestInfo.fromResultSet(row, user) }
            .firstOrNull() ?: throw RequestFetchError("Not found")
}
