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
import ru.ac.uniyar.domain.operations.ListAnnouncementsByCategoryTitleAndCityOperation
import ru.ac.uniyar.domain.operations.ListCategoriesOperation
import ru.ac.uniyar.domain.operations.ListCitiesOperation
import ru.ac.uniyar.domain.operations.UriWithPageQuery
import ru.ac.uniyar.web.lenses.PageLens
import ru.ac.uniyar.web.lenses.lensOrDefault
import ru.ac.uniyar.web.models.AnnouncementsListVM

class ShowAnnouncementsListHandler(
    private val htmlView: BiDiBodyLens<ViewModel>,
    private val listAnnouncementsByCategoryTitleAndCityOperation: ListAnnouncementsByCategoryTitleAndCityOperation,
    private val listCategoriesOperation: ListCategoriesOperation,
    private val listCitiesOperation: ListCitiesOperation,
) : HttpHandler {
    companion object {
        private val titleLens = Query.string().optional("title")
        private val categoryLens = Query.string().optional("category")
        private val cityLens = Query.string().optional("city")
    }
    override fun invoke(request: Request): Response {
        val page = PageLens(request).getPage()
        val title = lensOrDefault(titleLens, request, "")
        val category = lensOrDefault(categoryLens, request, "")
        val city = lensOrDefault(cityLens, request, "")
        val announcements = listAnnouncementsByCategoryTitleAndCityOperation.list(page, category, title, city)
        return Response(Status.OK).with(
            htmlView of AnnouncementsListVM(
                announcements,
                UriWithPageQuery(request.uri, page),
                category,
                title,
                city,
                listCategoriesOperation.list(),
                listCitiesOperation.list(),
            )
        )
    }
}
