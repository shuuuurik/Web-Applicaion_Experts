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
        "/registration" bind Method.GET to handlerHolder.showUserCreationFormHandler,
        "/registration" bind Method.POST to handlerHolder.addUserHandler,
        "/login" bind Method.GET to handlerHolder.showLoginFormHandler,
        "/login" bind Method.POST to handlerHolder.authenticateUser,
        "/logout" bind Method.GET to handlerHolder.logOutUser,
        "/profile" bind Method.GET to handlerHolder.showProfileHandler,
        "/profile/edit" bind Method.GET to handlerHolder.showUserEditingFormHandler,
        "/profile/edit" bind Method.POST to handlerHolder.editUserHandler,
        "/announcements" bind Method.GET to handlerHolder.showAnnouncementsListHandler,
        "/cities/new" bind Method.GET to handlerHolder.showCityCreationFormHandler,
        "/cities/new" bind Method.POST to handlerHolder.addCityHandler,
        "/cities" bind Method.GET to handlerHolder.showCitiesListHandler,
        "/cities/{name}" bind Method.GET to handlerHolder.showCityHandler,
        "/categories/new" bind Method.GET to handlerHolder.showCategoryCreationFormHandler,
        "/categories/new" bind Method.POST to handlerHolder.addCategoryHandler,
        "/categories" bind Method.GET to handlerHolder.showCategoriesListHandler,
        "/categories/{index}" bind Method.GET to handlerHolder.showCategoryHandler,
        "/specialists/{login}/edit" bind Method.GET to handlerHolder.showSpecialistEditingFormHandler,
        "/specialists/{login}/edit" bind Method.POST to handlerHolder.editSpecialistHandler,
        "/specialists" bind Method.GET to handlerHolder.showSpecialistsListHandler,
        "/specialists/{login}" bind Method.GET to handlerHolder.showSpecialistHandler,
        "/requests/new" bind Method.GET to handlerHolder.showRequestCreationFormHandler,
        "/requests/new" bind Method.POST to handlerHolder.addRequestHandler,
        "/requests" bind Method.GET to handlerHolder.showRequestsListHandler,
        "/requests/{index}/discard" bind Method.GET to handlerHolder.showRequestDiscardingFormHandler,
        "/requests/{index}/discard" bind Method.POST to handlerHolder.discardRequestHandler,
        "/requests/{index}" bind Method.GET to handlerHolder.showRequestHandler,
        "/requests/{index}" bind Method.POST to handlerHolder.acceptRequestHandler,
        "/announcements/{index}/edit" bind Method.GET to handlerHolder.showAnnouncementEditingFormHandler,
        "/announcements/{index}/edit" bind Method.POST to handlerHolder.editAnnouncementHandler,
        "/announcements/new" bind Method.GET to handlerHolder.showCategoriesForAnnouncementFormHandler,
        "/announcements/new" bind Method.POST to handlerHolder.selectCategoryHandler,
        "/announcements/new/{index}" bind Method.GET to handlerHolder.showAnnouncementCreationFormHandler,
        "/announcements/new/{index}" bind Method.POST to handlerHolder.addAnnouncementHandler,
        "/announcements/{index}" bind Method.GET to handlerHolder.showAnnouncementHandler,
    )
}
