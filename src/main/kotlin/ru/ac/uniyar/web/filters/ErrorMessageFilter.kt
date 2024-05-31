package ru.ac.uniyar.web.filters

import org.http4k.core.Filter
import org.http4k.core.HttpHandler
import org.http4k.core.Request
import org.http4k.core.with
import org.http4k.lens.BiDiBodyLens
import org.http4k.template.ViewModel
import ru.ac.uniyar.web.models.ShowErrorInfoVM

fun showErrorMessageFilter(htmlView: BiDiBodyLens<ViewModel>) = Filter { next: HttpHandler ->
    { request: Request ->
        val response = next(request)
        if (response.status.clientError ||
            response.status.serverError ||
            response.header("content-type").isNullOrEmpty()
        ) {
            response.with(htmlView of ShowErrorInfoVM(request.uri))
        } else {
            response
        }
    }
}
