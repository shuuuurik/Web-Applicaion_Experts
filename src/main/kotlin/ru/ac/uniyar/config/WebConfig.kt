package ru.ac.uniyar.config

import org.http4k.cloudnative.env.Environment
import org.http4k.cloudnative.env.EnvironmentKey
import org.http4k.lens.int

class WebConfig(
    val webPort: Int,
) {
    companion object {
        val webPortLens = EnvironmentKey.int().required("web.port", "Application web port")
        fun createWebConfig(env: Environment): WebConfig = WebConfig(webPortLens(env))
        val defaultEnv: Environment = Environment.defaults(
            webPortLens of 1515,
        )
    }
}
