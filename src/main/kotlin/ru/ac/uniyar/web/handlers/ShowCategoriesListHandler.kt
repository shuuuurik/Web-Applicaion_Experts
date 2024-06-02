package ru.ac.uniyar.web.handlers

import org.http4k.core.HttpHandler
import org.http4k.core.Request
import org.http4k.core.Response
import org.http4k.core.Status
import org.http4k.core.with
import org.http4k.lens.Query
import org.http4k.lens.string
import ru.ac.uniyar.domain.database.entities.UriWithPageQuery
import ru.ac.uniyar.domain.operations.ListCategoriesByNameOperation
import ru.ac.uniyar.web.lenses.PageLens
import ru.ac.uniyar.web.lenses.lensOrDefault
import ru.ac.uniyar.web.models.CategoriesListVM
import ru.ac.uniyar.web.templates.ContextAwareViewRender

class ShowCategoriesListHandler(
    private val htmlView: ContextAwareViewRender,
    private val listCategoriesByNameOperation: ListCategoriesByNameOperation
) : HttpHandler {
    companion object {
        private val nameLens = Query.string().optional("name")
    }
    override fun invoke(request: Request): Response {
        val page = PageLens(request).getPage()
        val name = lensOrDefault(nameLens, request, "")
        val categories = listCategoriesByNameOperation.list(page, name)
        return Response(Status.OK).with(
            htmlView(request) of CategoriesListVM(
                categories,
                UriWithPageQuery(request.uri, page),
                name,
            )
        )
    }
}
