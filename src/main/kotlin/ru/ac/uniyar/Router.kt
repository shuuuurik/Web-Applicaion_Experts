package ru.ac.uniyar

import org.http4k.core.Method
import org.http4k.routing.RoutingHttpHandler
import org.http4k.routing.bind
import org.http4k.routing.routes
import ru.ac.uniyar.web.handlers.HttpHandlerHolder

class Router(
    private val handlerHolder: HttpHandlerHolder,
) {
    operator fun invoke(): RoutingHttpHandler = routes(
        "/" bind Method.GET to handlerHolder.redirectToHomeHandler,
        "/home" bind Method.GET to handlerHolder.showHomePageHandler,
        "/home/erm" bind Method.GET to handlerHolder.showERMHandler,
        "/announcements/new" bind Method.GET to handlerHolder.showAnnouncementCreationFormHandler,
        "/announcements/new" bind Method.POST to handlerHolder.addAnnouncementHandler,
        "/announcements" bind Method.GET to handlerHolder.showAnnouncementsListHandler,
        "/announcements/{index}" bind Method.GET to handlerHolder.showAnnouncementHandler,
        "/cities/new" bind Method.GET to handlerHolder.showCityCreationFormHandler,
        "/cities/new" bind Method.POST to handlerHolder.addCityHandler,
        "/cities" bind Method.GET to handlerHolder.showCitiesListHandler,
        "/cities/{name}" bind Method.GET to handlerHolder.showCityHandler,
        "/categories/new" bind Method.GET to handlerHolder.showCategoryCreationFormHandler,
        "/categories/new" bind Method.POST to handlerHolder.addCategoryHandler,
        "/categories" bind Method.GET to handlerHolder.showCategoriesListHandler,
        "/categories/{name}" bind Method.GET to handlerHolder.showCategoryHandler,
        "/specialists/new" bind Method.GET to handlerHolder.showSpecialistCreationFormHandler,
        "/specialists/new" bind Method.POST to handlerHolder.addSpecialistHandler,
        "/specialists" bind Method.GET to handlerHolder.showSpecialistsListHandler,
        "/specialists/{index}/new" bind Method.GET to handlerHolder.showAnnouncementForSpecialistCreationFormHandler,
        "/specialists/{index}/new" bind Method.POST to handlerHolder.addAnnouncementForSpecialistHandler,
        "/specialists/{index}" bind Method.GET to handlerHolder.showSpecialistHandler,
    )
}
