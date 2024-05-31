package ru.ac.uniyar.web.handlers

import org.http4k.core.HttpHandler
import org.http4k.core.Request
import org.http4k.core.Response
import org.http4k.core.Status
import org.http4k.core.with
import org.http4k.lens.BiDiBodyLens
import org.http4k.lens.Query
import org.http4k.lens.string
import org.http4k.template.ViewModel
import ru.ac.uniyar.domain.operations.ListSpecialistsByNameAndPhoneOperation
import ru.ac.uniyar.domain.operations.UriWithPageQuery
import ru.ac.uniyar.web.lenses.PageLens
import ru.ac.uniyar.web.lenses.lensOrDefault
import ru.ac.uniyar.web.models.SpecialistsListVM

class ShowSpecialistsListHandler(
    private val htmlView: BiDiBodyLens<ViewModel>,
    private val listSpecialistsByNameAndPhoneOperation: ListSpecialistsByNameAndPhoneOperation
) : HttpHandler {
    companion object {
        private val fullNameLens = Query.string().optional("full_name")
        private val phoneLens = Query.string().optional("phone")
    }
    override fun invoke(request: Request): Response {
        val page = PageLens(request).getPage()
        val fullName = lensOrDefault(fullNameLens, request, "")
        val phone = lensOrDefault(phoneLens, request, "")
        val specialists = listSpecialistsByNameAndPhoneOperation.list(page, fullName, phone)
        return Response(Status.OK).with(
            htmlView of SpecialistsListVM(
                specialists,
                UriWithPageQuery(request.uri, page),
                fullName,
                phone,
            )
        )
    }
}
