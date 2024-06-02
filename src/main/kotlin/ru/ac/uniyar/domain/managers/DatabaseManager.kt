package ru.ac.uniyar.domain.managers

import org.ktorm.database.Database
import org.ktorm.support.mysql.MySqlDialect
import ru.ac.uniyar.config.DataBaseConfig

fun connectToDatabase(databaseConfig: DataBaseConfig) = Database.connect(
    url = databaseConfig.createJDBC(),
    driver = "org.h2.Driver",
    user = databaseConfig.dbUsername,
    dialect = MySqlDialect(),
)
