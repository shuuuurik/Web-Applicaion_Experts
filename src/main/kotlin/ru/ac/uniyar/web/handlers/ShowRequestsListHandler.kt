package ru.ac.uniyar.web.handlers

import org.http4k.core.HttpHandler
import org.http4k.core.Request
import org.http4k.core.Response
import org.http4k.core.Status
import org.http4k.core.with
import org.http4k.lens.Query
import org.http4k.lens.RequestContextLens
import org.http4k.lens.string
import ru.ac.uniyar.domain.database.entities.RolePermissions
import ru.ac.uniyar.domain.database.entities.UriWithPageQuery
import ru.ac.uniyar.domain.database.entities.User
import ru.ac.uniyar.domain.operations.ListRequestsByStatusOperation
import ru.ac.uniyar.web.lenses.PageLens
import ru.ac.uniyar.web.lenses.lensOrDefault
import ru.ac.uniyar.web.models.RequestsListVM
import ru.ac.uniyar.web.templates.ContextAwareViewRender

class ShowRequestsListHandler(
    private val currentUserLens: RequestContextLens<User?>,
    private val permissionsLens: RequestContextLens<RolePermissions>,
    private val htmlView: ContextAwareViewRender,
    private val listRequestsByStatusOperation: ListRequestsByStatusOperation,
) : HttpHandler {
    companion object {
        private val statusLens = Query.string().optional("status")
    }
    override fun invoke(request: Request): Response {
        val permissions = permissionsLens(request)
        if (!permissions.listRequests) {
            return Response(Status.UNAUTHORIZED)
        }
        val user = currentUserLens(request)!!
        val page = PageLens(request).getPage()
        val status = lensOrDefault(statusLens, request, "")
        val requests = if (permissions.name == "Администратор")
            listRequestsByStatusOperation.list(page, status)
        else
            listRequestsByStatusOperation.list(page, status, user.username)
        return Response(Status.OK).with(
            htmlView(request) of RequestsListVM(
                requests,
                status,
                UriWithPageQuery(request.uri, page),
            )
        )
    }
}
