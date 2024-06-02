package ru.ac.uniyar.domain.operations

import org.ktorm.database.Database
import org.ktorm.dsl.and
import org.ktorm.dsl.eq
import org.ktorm.dsl.from
import org.ktorm.dsl.mapNotNull
import org.ktorm.dsl.select
import org.ktorm.dsl.where
import ru.ac.uniyar.domain.database.tables.RequestTable

class FetchDescriptionFromRequestOperation(
    private val database: Database,
) {
    fun fetch(username: String, categoryId: Int): String? =
        database
            .from(RequestTable)
            .select(RequestTable.education, RequestTable.experience)
            .where {
                (RequestTable.username eq username) and (RequestTable.categoryId eq categoryId) and
                    (RequestTable.status eq "Подтверждена")
            }
            .mapNotNull { row ->
                "Образование, сертификация:\n" + row.getString(1) +
                    "\n\nОпыт работы в данной категории:\n" + row.getString(2)
            }
            .firstOrNull()
}
