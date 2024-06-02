package ru.ac.uniyar.config

import org.http4k.cloudnative.env.Environment
import org.http4k.cloudnative.env.EnvironmentKey
import org.http4k.lens.nonEmptyString

class SaltConfig(
    val salt: String,
) {
    companion object {
        val saltLens = EnvironmentKey.nonEmptyString().required("salt")
        fun createSaltConfig(env: Environment): SaltConfig = SaltConfig(saltLens(env))
        val defaultEnv: Environment = Environment.defaults(
            saltLens of "jt34jfjfjkpeka[3sdfgl4pof;dfl4ldspehj8erpkfjfhh60yhk450-k90ujf89ktjgjrtopjhrgjkdfiopjfgoj" +
                "vcxmvbnervnmionjjiofhniojfoperjgioerbnghiofgdofmnfnjjfgnujirbghujibhdfiojfkl34nklfopj45iognjjkernio",
        )
    }
}
