package ru.ac.uniyar.web.filters

import org.http4k.core.Filter
import org.http4k.core.HttpHandler
import org.http4k.core.Request
import org.http4k.core.with
import ru.ac.uniyar.web.models.ShowErrorInfoVM
import ru.ac.uniyar.web.templates.ContextAwareViewRender

fun showErrorMessageFilter(
    htmlView: ContextAwareViewRender,
) = Filter { next: HttpHandler ->
    { request: Request ->
        val response = next(request)
        if (response.status.clientError ||
            response.status.serverError ||
            response.header("content-type").isNullOrEmpty()
        ) {
            response.with(htmlView(request) of ShowErrorInfoVM(request.uri))
        } else {
            response
        }
    }
}
