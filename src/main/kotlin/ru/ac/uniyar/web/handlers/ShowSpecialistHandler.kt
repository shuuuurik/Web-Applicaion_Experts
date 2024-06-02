package ru.ac.uniyar.web.handlers

import org.http4k.core.HttpHandler
import org.http4k.core.Request
import org.http4k.core.Response
import org.http4k.core.Status
import org.http4k.core.with
import org.http4k.lens.Path
import org.http4k.lens.RequestContextLens
import org.http4k.lens.string
import ru.ac.uniyar.domain.database.entities.RolePermissions
import ru.ac.uniyar.domain.database.entities.UriWithPageQuery
import ru.ac.uniyar.domain.operations.FetchSpecialistOperation
import ru.ac.uniyar.domain.operations.ListAnnouncementsBySpecialistOperation
import ru.ac.uniyar.web.lenses.PageLens
import ru.ac.uniyar.web.lenses.lensOrNull
import ru.ac.uniyar.web.models.SpecialistVM
import ru.ac.uniyar.web.templates.ContextAwareViewRender

class ShowSpecialistHandler(
    private val permissionsLens: RequestContextLens<RolePermissions>,
    private val htmlView: ContextAwareViewRender,
    private val fetchSpecialistOperation: FetchSpecialistOperation,
    private val listAnnouncementsBySpecialistOperation: ListAnnouncementsBySpecialistOperation
) : HttpHandler {
    companion object {
        private val loginLens = Path.string().of("login")
    }

    @Suppress("ReturnCount")
    override fun invoke(request: Request): Response {
        val permissions = permissionsLens(request)
        if (!permissions.showSpecialist) {
            return Response(Status.UNAUTHORIZED)
        }
        val specialistLogin = lensOrNull(loginLens, request) ?: return Response(Status.BAD_REQUEST)
        val specialist = fetchSpecialistOperation.fetch(specialistLogin) ?: return Response(Status.BAD_REQUEST)
        val page = PageLens(request).getPage()
        val announcements = listAnnouncementsBySpecialistOperation.list(page, specialist.username)
        return Response(Status.OK).with(
            htmlView(request) of SpecialistVM(
                specialist,
                announcements,
                UriWithPageQuery(request.uri, page),
            )
        )
    }
}
