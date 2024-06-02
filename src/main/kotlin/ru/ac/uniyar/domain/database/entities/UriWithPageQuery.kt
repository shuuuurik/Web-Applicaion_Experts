package ru.ac.uniyar.domain.database.entities

import org.http4k.core.Uri
import org.http4k.core.query
import org.http4k.core.removeQuery

data class UriWithPageQuery(val uri: Uri, val page: Int = 1) {
    val nextPage = uri.removeQuery("page")
        .query("page", (page + 1).toString())
        .toString()

    val previousPage = uri.removeQuery("page")
        .query("page", (page - 1).toString())
        .toString()
}
