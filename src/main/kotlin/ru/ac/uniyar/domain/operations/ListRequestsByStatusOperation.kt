package ru.ac.uniyar.domain.operations

import org.ktorm.database.Database
import org.ktorm.dsl.and
import org.ktorm.dsl.asc
import org.ktorm.dsl.eq
import org.ktorm.dsl.from
import org.ktorm.dsl.leftJoin
import org.ktorm.dsl.like
import org.ktorm.dsl.limit
import org.ktorm.dsl.mapNotNull
import org.ktorm.dsl.orderBy
import org.ktorm.dsl.select
import org.ktorm.dsl.where
import ru.ac.uniyar.domain.database.entities.Request
import ru.ac.uniyar.domain.database.tables.CategoryTable
import ru.ac.uniyar.domain.database.tables.RequestTable

class ListRequestsByStatusOperation(
    private val database: Database,
) {
    companion object {
        const val REQUESTS_PER_PAGE = 5
    }
    fun list(page: Int, status: String, username: String = ""): List<Request> =
        database
            .from(RequestTable)
            .leftJoin(CategoryTable, on = RequestTable.categoryId eq CategoryTable.id)
            .select()
            .where { (RequestTable.username like "%$username%") and (RequestTable.status like "%$status%") }
            .orderBy(RequestTable.addingTime.asc())
            .limit((page - 1) * REQUESTS_PER_PAGE, REQUESTS_PER_PAGE)
            .mapNotNull(Request::fromResultSet)
}
