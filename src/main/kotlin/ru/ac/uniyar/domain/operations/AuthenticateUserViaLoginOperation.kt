package ru.ac.uniyar.domain.operations

import org.ktorm.database.Database
import org.ktorm.database.asIterable

class AuthenticateUserViaLoginOperation(
    private val database: Database,
    private val salt: String
) {
    private val preparedSql = """
        select USERNAME from USERS
        where USERNAME = ? and PASSWORD = HASH('SHA3-256', ?)
    """.trimIndent()

    fun authenticate(username: String, password: String) =
        database
            .useConnection { connection ->
                connection.prepareStatement(preparedSql).use { statement ->
                    statement.setString(1, username)
                    statement.setString(2, password + salt)
                    statement
                        .executeQuery()
                        .asIterable()
                        .map { row ->
                            row.getString(1)
                        }.firstOrNull() ?: throw AuthenticationError()
                }
            }
}
