package ru.ac.uniyar.web.handlers

import org.http4k.core.HttpHandler
import org.http4k.core.Request
import org.http4k.core.Response
import org.http4k.core.Status
import org.http4k.core.with
import org.http4k.lens.Path
import org.http4k.lens.int
import ru.ac.uniyar.domain.operations.FetchAnnouncementOperation
import ru.ac.uniyar.domain.operations.FetchUserViaUsernameOperation
import ru.ac.uniyar.web.lenses.lensOrNull
import ru.ac.uniyar.web.models.AnnouncementVM
import ru.ac.uniyar.web.templates.ContextAwareViewRender

class ShowAnnouncementHandler(
    private val htmlView: ContextAwareViewRender,
    private val fetchAnnouncementOperation: FetchAnnouncementOperation,
    private val fetchUserViaUsernameOperation: FetchUserViaUsernameOperation
) : HttpHandler {
    companion object {
        private val indexLens = Path.int().of("index")
    }

    @Suppress("ReturnCount")
    override fun invoke(request: Request): Response {
        val announcementId = lensOrNull(indexLens, request) ?: return Response(Status.BAD_REQUEST)
        val announcement = fetchAnnouncementOperation.fetch(announcementId)
            ?: return Response(Status.BAD_REQUEST)
        val specialist = fetchUserViaUsernameOperation.fetch(announcement.specialistUsername)!!
        return Response(Status.OK).with(
            htmlView(request) of AnnouncementVM(announcement, specialist.fullName)
        )
    }
}
