package ru.ac.uniyar.domain.operations

import org.ktorm.database.Database

class AddUserOperation(
    private val database: Database,
    private val salt: String
) {
    private val preparedSql = """
        INSERT INTO USERS(USERNAME, PASSWORD, FULL_NAME, PHONE, CITY_NAME, ROLE_ID)
            VALUES (?, HASH('SHA3-256', ?), ?, ?, ?, 1)
    """.trimIndent()

    @Suppress("MagicNumber")
    fun add(username: String, password: String, fullName: String, phone: String, city: String) {
        database.useConnection { connection ->
            connection.prepareStatement(preparedSql).use { statement ->
                statement.setString(1, username)
                statement.setString(2, password + salt)
                statement.setString(3, fullName)
                statement.setString(4, phone)
                statement.setString(5, city)
                statement.executeUpdate()
            }
        }
    }
}
