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
import ru.ac.uniyar.domain.operations.ListCitiesByNameOperation
import ru.ac.uniyar.domain.operations.UriWithPageQuery
import ru.ac.uniyar.web.lenses.PageLens
import ru.ac.uniyar.web.lenses.lensOrDefault
import ru.ac.uniyar.web.models.CitiesListVM

class ShowCitiesListHandler(
    private val htmlView: BiDiBodyLens<ViewModel>,
    private val listCitiesByNameOperation: ListCitiesByNameOperation
) : HttpHandler {
    companion object {
        private val nameLens = Query.string().optional("name")
    }
    override fun invoke(request: Request): Response {
        val page = PageLens(request).getPage()
        val name = lensOrDefault(nameLens, request, "")
        val cities = listCitiesByNameOperation.list(page, name)
        return Response(Status.OK).with(
            htmlView of CitiesListVM(
                cities,
                UriWithPageQuery(request.uri, page),
                name,
            )
        )
    }
}