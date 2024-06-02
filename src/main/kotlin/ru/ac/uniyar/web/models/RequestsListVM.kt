package ru.ac.uniyar.web.models

import org.http4k.template.ViewModel
import ru.ac.uniyar.domain.database.entities.Request
import ru.ac.uniyar.domain.database.entities.UriWithPageQuery

data class RequestsListVM(
    val requests: List<Request>,
    val status: String,
    val uri: UriWithPageQuery,
) : ViewModel
