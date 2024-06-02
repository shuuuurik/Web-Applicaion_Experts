package ru.ac.uniyar.web.handlers

import org.http4k.core.HttpHandler
import org.http4k.core.Request
import org.http4k.core.Response
import org.http4k.core.Status
import org.http4k.core.with
import ru.ac.uniyar.domain.operations.CountAnnouncementsOperation
import ru.ac.uniyar.domain.operations.CountSpecialistsOperation
import ru.ac.uniyar.domain.operations.ListUsersOperation
import ru.ac.uniyar.web.models.ERMPageVM
import ru.ac.uniyar.web.models.HomePageVM
import ru.ac.uniyar.web.templates.ContextAwareViewRender
import kotlin.math.roundToInt

class ShowHomePageHandler(
    private val htmlView: ContextAwareViewRender,
    private val countSpecialistsOperation: CountSpecialistsOperation,
    private val countAnnouncementsOperation: CountAnnouncementsOperation,
    private val listUsersOperation: ListUsersOperation
) : HttpHandler {
    override fun invoke(request: Request): Response {
        val specialistsNumber = countSpecialistsOperation.count()
        val announcementsNumber = countAnnouncementsOperation.count()
        val avgAnnouncementNumber =
            if (specialistsNumber == 0)
                0.toFloat()
            else
                (announcementsNumber.toFloat() / specialistsNumber.toFloat() * 10.toFloat()).roundToInt() / 10.toFloat()
        return Response(Status.OK).with(
            htmlView(request) of HomePageVM(specialistsNumber, avgAnnouncementNumber, listUsersOperation.list())
        )
    }
}

class RedirectToHomeHandler : HttpHandler {
    override fun invoke(request: Request): Response {
        return Response(Status.FOUND).header("Location", "/home")
    }
}

class ShowERMHandler(
    private val htmlView: ContextAwareViewRender
) : HttpHandler {
    override fun invoke(request: Request): Response {
        return Response(Status.OK).with(htmlView(request) of ERMPageVM())
    }
}
