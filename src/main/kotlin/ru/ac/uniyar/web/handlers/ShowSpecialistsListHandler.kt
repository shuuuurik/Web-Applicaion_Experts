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
import ru.ac.uniyar.domain.operations.ListSpecialistsByNameAndPhoneOperation
import ru.ac.uniyar.web.lenses.PageLens
import ru.ac.uniyar.web.lenses.lensOrDefault
import ru.ac.uniyar.web.models.SpecialistsListVM
import ru.ac.uniyar.web.templates.ContextAwareViewRender

class ShowSpecialistsListHandler(
    private val permissionsLens: RequestContextLens<RolePermissions>,
    private val htmlView: ContextAwareViewRender,
    private val listSpecialistsByNameAndPhoneOperation: ListSpecialistsByNameAndPhoneOperation
) : HttpHandler {
    companion object {
        private val fullNameLens = Query.string().optional("full_name")
        private val phoneLens = Query.string().optional("phone")
    }
    override fun invoke(request: Request): Response {
        val permissions = permissionsLens(request)
        if (!permissions.listSpecialists) {
            return Response(Status.UNAUTHORIZED)
        }
        val page = PageLens(request).getPage()
        val fullName = lensOrDefault(fullNameLens, request, "")
        val phone = lensOrDefault(phoneLens, request, "")
        val specialists = listSpecialistsByNameAndPhoneOperation.list(page, fullName, phone)
        return Response(Status.OK).with(
            htmlView(request) of SpecialistsListVM(
                specialists,
                UriWithPageQuery(request.uri, page),
                fullName,
                phone,
            )
        )
    }
}
