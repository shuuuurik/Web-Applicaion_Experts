package ru.ac.uniyar.config

import org.http4k.cloudnative.env.Environment
import org.http4k.cloudnative.env.EnvironmentKey
import org.http4k.lens.int
import org.http4k.lens.nonEmptyString
import org.http4k.lens.string

class DataBaseConfig(
    private val dbHost: String,
    val dbPort: Int,
    private val dbName: String,
    val dbUsername: String,
    val dbPassword: String?
) {
    companion object {
        val dbHostLens = EnvironmentKey.string().required("db.host")
        val dbPortLens = EnvironmentKey.int().required("db.port")
        val dbNameLens = EnvironmentKey.nonEmptyString().required("db.name")
        val dbUsernameLens = EnvironmentKey.nonEmptyString().required("db.username")
        val dbPasswordLens = EnvironmentKey.string().optional("db.password")
        fun createDatabaseConfig(env: Environment): DataBaseConfig = DataBaseConfig(
            dbHostLens(env),
            dbPortLens(env),
            dbNameLens(env),
            dbUsernameLens(env),
            dbPasswordLens(env)
        )
        val defaultEnv: Environment = Environment.defaults(
            dbHostLens of "localhost",
            dbPortLens of 8082,
            dbNameLens of "database.h2",
            dbUsernameLens of "sa",
            dbPasswordLens of null
        )
    }
    fun createJDBC() = "jdbc:h2:tcp://$dbHost/$dbName"
}
