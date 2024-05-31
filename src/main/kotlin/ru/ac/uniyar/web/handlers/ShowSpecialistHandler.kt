package ru.ac.uniyar.web.handlers

import org.http4k.core.HttpHandler
import org.http4k.core.Request
import org.http4k.core.Response
import org.http4k.core.Status
import org.http4k.core.with
import org.http4k.lens.BiDiBodyLens
import org.http4k.lens.Path
import org.http4k.lens.int
import org.http4k.template.ViewModel
import ru.ac.uniyar.domain.operations.FetchSpecialistOperation
import ru.ac.uniyar.domain.operations.ListAnnouncementsBySpecialistOperation
import ru.ac.uniyar.domain.operations.UriWithPageQuery
import ru.ac.uniyar.web.lenses.PageLens
import ru.ac.uniyar.web.lenses.lensOrNull
import ru.ac.uniyar.web.models.SpecialistVM

class ShowSpecialistHandler(
    private val htmlView: BiDiBodyLens<ViewModel>,
    private val fetchSpecialistOperation: FetchSpecialistOperation,
    private val listAnnouncementsBySpecialistOperation: ListAnnouncementsBySpecialistOperation
) : HttpHandler {
    companion object {
        private val indexLens = Path.int().of("index")
    }
    override fun invoke(request: Request): Response {
        val specialistId = lensOrNull(indexLens, request) ?: return Response(Status.BAD_REQUEST)
        val specialist = fetchSpecialistOperation.fetch(specialistId) ?: return Response(Status.BAD_REQUEST)
        val page = PageLens(request).getPage()
        val announcements = listAnnouncementsBySpecialistOperation.list(page, specialist.fullName)
        return Response(Status.OK).with(
            htmlView of SpecialistVM(
                specialist,
                announcements,
                UriWithPageQuery(request.uri, page),
            )
        )
    }
}
