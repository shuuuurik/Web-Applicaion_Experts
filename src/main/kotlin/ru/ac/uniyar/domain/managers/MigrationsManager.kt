package ru.ac.uniyar.domain.managers

import org.flywaydb.core.Flyway
import ru.ac.uniyar.config.DataBaseConfig

fun performMigrations(databaseConfig: DataBaseConfig) {
    val flyway = Flyway
        .configure()
        .locations("ru/ac/uniyar/db/migrations")
        .validateMigrationNaming(true)
        .dataSource(databaseConfig.createJDBC(), databaseConfig.dbUsername, databaseConfig.dbPassword)
        .load()
    flyway.migrate()
}
