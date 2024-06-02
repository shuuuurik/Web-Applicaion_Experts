package ru.ac.uniyar.domain.operations

import org.ktorm.database.Database
import org.ktorm.database.asIterable
import ru.ac.uniyar.domain.database.entities.Category
import java.time.format.DateTimeFormatter

class ListCategoriesForRequestOperation(
    private val database: Database,
) {
    private val preparedSql = """
        SELECT * FROM CATEGORY
        EXCEPT
        SELECT c.ID, c.NAME, c.ADDING_TIME
        FROM REQUEST r
        LEFT JOIN CATEGORY c
        ON r.CATEGORY_ID = c.ID
        WHERE (r.STATUS = 'Новая' OR r.STATUS = 'Подтверждена') AND r.USERS_USERNAME = ?
    """.trimIndent()

    @Suppress("MagicNumber")
    fun list(username: String): List<Category> {
        database.useConnection { connection ->
            connection.prepareStatement(preparedSql).use { statement ->
                statement.setString(1, username)
                return statement
                    .executeQuery()
                    .asIterable()
                    .mapNotNull { row ->
                        Category(
                            row.getInt(1),
                            row.getString(2),
                            row.getString(3).format(DateTimeFormatter.ofPattern("dd.MM.yy, HH:mm"))
                        )
                    }
            }
        }
    }
}
