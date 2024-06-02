package ru.ac.uniyar.web.handlers

import org.http4k.core.HttpHandler
import org.http4k.core.Request
import org.http4k.core.Response
import org.http4k.core.Status
import org.http4k.core.with
import org.http4k.lens.Path
import org.http4k.lens.int
import ru.ac.uniyar.domain.operations.FetchCategoryOperation
import ru.ac.uniyar.web.lenses.lensOrNull
import ru.ac.uniyar.web.models.CategoryVM
import ru.ac.uniyar.web.templates.ContextAwareViewRender

class ShowCategoryHandler(
    private val htmlView: ContextAwareViewRender,
    private val fetchCategoryOperation: FetchCategoryOperation
) : HttpHandler {
    companion object {
        private val indexLens = Path.int().of("index")
    }
    @Suppress("ReturnCount")
    override fun invoke(request: Request): Response {
        val categoryId = lensOrNull(indexLens, request) ?: return Response(Status.BAD_REQUEST)
        val category = fetchCategoryOperation.fetch(categoryId) ?: return Response(Status.BAD_REQUEST)
        return Response(Status.OK).with(htmlView(request) of CategoryVM(category))
    }
}
