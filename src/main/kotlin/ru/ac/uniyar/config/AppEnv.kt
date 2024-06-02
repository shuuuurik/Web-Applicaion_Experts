package ru.ac.uniyar.config

import org.http4k.cloudnative.env.Environment

val appEnv = Environment.fromResource("/ru/ac/uniyar/config/app.properties") overrides
    Environment.JVM_PROPERTIES overrides
    Environment.ENV overrides
    WebConfig.defaultEnv overrides
    DataBaseConfig.defaultEnv overrides
    SaltConfig.defaultEnv

fun readConfiguration(): AppConfig = AppConfig(
    WebConfig.createWebConfig(appEnv),
    DataBaseConfig.createDatabaseConfig(appEnv),
    SaltConfig.createSaltConfig(appEnv),
)
