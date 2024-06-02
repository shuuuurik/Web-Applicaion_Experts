package ru.ac.uniyar.config

data class AppConfig(
    val webConfig: WebConfig,
    val databaseConfig: DataBaseConfig,
    val saltConfig: SaltConfig,
)
