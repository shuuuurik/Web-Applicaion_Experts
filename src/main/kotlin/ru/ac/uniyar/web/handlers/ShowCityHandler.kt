package ru.ac.uniyar.web.handlers

import org.http4k.core.HttpHandler
import org.http4k.core.Request
import org.http4k.core.Response
import org.http4k.core.Status
import org.http4k.core.with
import org.http4k.lens.Path
import org.http4k.lens.string
import ru.ac.uniyar.domain.operations.FetchCityOperation
import ru.ac.uniyar.domain.operations.ListCategoriesByCityOperation
import ru.ac.uniyar.web.lenses.lensOrNull
import ru.ac.uniyar.web.models.CityVM
import ru.ac.uniyar.web.templates.ContextAwareViewRender

class ShowCityHandler(
    private val htmlView: ContextAwareViewRender,
    private val fetchCityOperation: FetchCityOperation,
    private val listCategoriesByCityOperation: ListCategoriesByCityOperation
) : HttpHandler {
    companion object {
        private val nameLens = Path.string().of("name")
    }
    @Suppress("ReturnCount")
    override fun invoke(request: Request): Response {
        val cityName = lensOrNull(nameLens, request) ?: return Response(Status.BAD_REQUEST)
        val city = fetchCityOperation.fetch(cityName) ?: return Response(Status.BAD_REQUEST)
        return Response(Status.OK).with(
            htmlView(request) of CityVM(city, listCategoriesByCityOperation.list((cityName)))
        )
    }
}
