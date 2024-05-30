package ru.ac.uniyar

import org.http4k.core.*
import org.http4k.core.Method.GET
import org.http4k.core.Status.Companion.OK
import org.http4k.core.body.formAsMap
import org.http4k.routing.*
import org.http4k.server.Undertow
import org.http4k.server.asServer
import org.http4k.template.PebbleTemplates
import org.http4k.template.TemplateRenderer
import ru.ac.uniyar.domain.Announcement
import ru.ac.uniyar.domain.Announcements
import ru.ac.uniyar.models.AnnouncementViewModel
import ru.ac.uniyar.models.AnnouncementsListViewModel
import ru.ac.uniyar.models.HomePageViewModel
import ru.ac.uniyar.models.NewAnnouncementDataViewModel

fun showHomePage(renderer: TemplateRenderer): HttpHandler = {
    val viewModel = HomePageViewModel()
    val renderedView = renderer(viewModel)
    Response(OK).body(renderedView)
}

fun showAnnouncementsList(renderer: TemplateRenderer, announcements: Announcements): HttpHandler = {
    val viewModel = AnnouncementsListViewModel(announcements.fetchAllAnnouncements())
    Response(OK).body(renderer(viewModel))
}

fun showNewAnnouncementForm(renderer: TemplateRenderer):HttpHandler = {
    val viewModel = NewAnnouncementDataViewModel()
    val renderedView = renderer(viewModel)
    Response(OK).body(renderedView)
}

fun createNewAnnouncement(announcements: Announcements):HttpHandler = {request ->
    val form = request.formAsMap()
    val newAnnouncement = Announcement(
        serviceCategory = form.getFirst("category").orEmpty(),
        title = form.getFirst("title").orEmpty(),
        description = form.getFirst("description").orEmpty(),
        specialist = form.getFirst("specialist").orEmpty(),
        education = form.getFirst("education").orEmpty(),
        phoneNumber = form.getFirst("phone").orEmpty(),
    )
    announcements.add(newAnnouncement)
    Response(Status.FOUND).header("Location", "/announcements")
}

fun showAnnouncement(renderer: TemplateRenderer, announcements: Announcements): HttpHandler = { req ->
    val number = req.path("id")?.toIntOrNull()
    number?.let{
        val announcement = announcements.announcements[number]
        val viewModel = AnnouncementViewModel(announcement)
        val renderedView = renderer(viewModel)
        Response(OK).body(renderedView)
    }?:Response(Status.BAD_REQUEST)
}

fun redirectToHome():HttpHandler = {
    Response(Status.FOUND).header("Location", "/home")
}

fun app(renderer: TemplateRenderer, announcements: Announcements): HttpHandler = routes(
    "/home" bind GET to showHomePage(renderer),
    "/announcements" bind GET to showAnnouncementsList(renderer, announcements),
    "/announcements/new" bind GET to showNewAnnouncementForm(renderer),
    "/announcements/new" bind Method.POST to createNewAnnouncement(announcements),
    "/announcements/{id}" bind GET to showAnnouncement(renderer, announcements),
    "/" bind GET to redirectToHome(),
    static(ResourceLoader.Classpath("/ru/ac/uniyar/public/")),
)

fun main() {
    val announcements = Announcements()
    announcements.add(Announcement("Development", "Unreal Engine Developer", "Разрабатываю игры на Unreal Engine с использованием C++",
    "Иванов Иван Иванович", "Неполное высшее образование", "88005553535"))

    val renderer = PebbleTemplates().HotReload("src/main/resources")
    //val printingApp: HttpHandler = PrintRequestAndResponse().then(app(renderer, announcements))
    val server = app(renderer, announcements).asServer(Undertow(9000)).start()
    println("Server started on http://localhost:" + server.port())
}
