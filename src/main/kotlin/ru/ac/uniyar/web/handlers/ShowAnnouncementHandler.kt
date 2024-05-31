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
import ru.ac.uniyar.domain.operations.FetchAnnouncementOperation
import ru.ac.uniyar.domain.operations.FetchSpecialistOperation
import ru.ac.uniyar.web.lenses.lensOrNull
import ru.ac.uniyar.web.models.AnnouncementVM

class ShowAnnouncementHandler(
    private val htmlView: BiDiBodyLens<ViewModel>,
    private val fetchAnnouncementOperation: FetchAnnouncementOperation,
    private val fetchSpecialistOperation: FetchSpecialistOperation
) : HttpHandler {
    companion object {
        private val indexLens = Path.int().of("index")
    }
    override fun invoke(request: Request): Response {
        val announcementId = lensOrNull(indexLens, request) ?: return Response(Status.BAD_REQUEST)
        val announcement = fetchAnnouncementOperation.fetch(announcementId)
            ?: return Response(Status.BAD_REQUEST)
        val specialist = fetchSpecialistOperation.fetch(announcement.specialistId)!!
        return Response(Status.OK).with(htmlView of AnnouncementVM(announcement, specialist.fullName))
    }
}
