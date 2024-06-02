package ru.ac.uniyar.web.handlers

import org.http4k.core.HttpHandler
import org.http4k.core.Request
import org.http4k.core.Response
import org.http4k.core.Status
import org.http4k.core.with
import org.http4k.lens.RequestContextLens
import ru.ac.uniyar.domain.database.entities.User
import ru.ac.uniyar.web.models.ProfilePageVM
import ru.ac.uniyar.web.templates.ContextAwareViewRender

class ShowProfileHandler(
    private val currentUserLens: RequestContextLens<User?>,
    private val htmlView: ContextAwareViewRender,
) : HttpHandler {
    override fun invoke(request: Request): Response {
        currentUserLens(request) ?: return Response(Status.UNAUTHORIZED)
        return Response(Status.OK).with(htmlView(request) of ProfilePageVM())
    }
}
