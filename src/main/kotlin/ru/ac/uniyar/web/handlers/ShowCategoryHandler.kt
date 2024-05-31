package ru.ac.uniyar.web.handlers

import org.http4k.core.HttpHandler
import org.http4k.core.Request
import org.http4k.core.Response
import org.http4k.core.Status
import org.http4k.core.with
import org.http4k.lens.BiDiBodyLens
import org.http4k.lens.Path
import org.http4k.lens.string
import org.http4k.template.ViewModel
import ru.ac.uniyar.domain.operations.FetchCategoryOperation
import ru.ac.uniyar.web.lenses.lensOrNull
import ru.ac.uniyar.web.models.CategoryVM

class ShowCategoryHandler(
    private val htmlView: BiDiBodyLens<ViewModel>,
    private val fetchCategoryOperation: FetchCategoryOperation
) : HttpHandler {
    companion object {
        private val nameLens = Path.string().of("name")
    }
    override fun invoke(request: Request): Response {
        val categoryName = lensOrNull(nameLens, request) ?: return Response(Status.BAD_REQUEST)
        val category = fetchCategoryOperation.fetch(categoryName) ?: return Response(Status.BAD_REQUEST)
        return Response(Status.OK).with(htmlView of CategoryVM(category))
    }
}
