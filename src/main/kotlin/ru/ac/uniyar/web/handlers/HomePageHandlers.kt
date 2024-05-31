package ru.ac.uniyar.web.handlers

import org.http4k.core.HttpHandler
import org.http4k.core.Request
import org.http4k.core.Response
import org.http4k.core.Status
import org.http4k.core.with
import org.http4k.lens.BiDiBodyLens
import org.http4k.template.ViewModel
import ru.ac.uniyar.domain.operations.CountAnnouncementsOperation
import ru.ac.uniyar.domain.operations.CountSpecialistsOperation
import ru.ac.uniyar.web.models.ERMPageVM
import ru.ac.uniyar.web.models.HomePageVM
import kotlin.math.roundToInt

class ShowHomePageHandler(
    private val htmlView: BiDiBodyLens<ViewModel>,
    private val countSpecialistsOperation: CountSpecialistsOperation,
    private val countAnnouncementsOperation: CountAnnouncementsOperation
) : HttpHandler {
    override fun invoke(request: Request): Response {
        val specialistsNumber = countSpecialistsOperation.count()
        val announcementsNumber = countAnnouncementsOperation.count()
        val avgAnnouncementNumber =
            (announcementsNumber.toFloat() / specialistsNumber.toFloat() * 10.toFloat()).roundToInt() / 10.toFloat()
        return Response(Status.OK).with(htmlView of HomePageVM(specialistsNumber, avgAnnouncementNumber))
    }
}

class RedirectToHomeHandler : HttpHandler {
    override fun invoke(request: Request): Response {
        return Response(Status.FOUND).header("Location", "/home")
    }
}

class ShowERMHandler(
    private val htmlView: BiDiBodyLens<ViewModel>
) : HttpHandler {
    override fun invoke(request: Request): Response {
        return Response(Status.OK).with(htmlView of ERMPageVM())
    }
}
