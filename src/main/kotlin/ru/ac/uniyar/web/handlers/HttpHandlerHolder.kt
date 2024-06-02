package ru.ac.uniyar.web.handlers

import org.http4k.lens.RequestContextLens
import ru.ac.uniyar.domain.database.entities.RolePermissions
import ru.ac.uniyar.domain.database.entities.User
import ru.ac.uniyar.domain.operations.OperationHolder
import ru.ac.uniyar.web.filters.JwtTools
import ru.ac.uniyar.web.templates.ContextAwareViewRender

class HttpHandlerHolder(
    currentUserLens: RequestContextLens<User?>,
    permissionsLens: RequestContextLens<RolePermissions>,
    htmlView: ContextAwareViewRender,
    operationHolder: OperationHolder,
    jwtTools: JwtTools
) {
    val showHomePageHandler = ShowHomePageHandler(
        htmlView,
        operationHolder.countSpecialistsOperation,
        operationHolder.countAnnouncementsOperation,
        operationHolder.listUsersOperation
    )
    val redirectToHomeHandler = RedirectToHomeHandler()
    val showERMHandler = ShowERMHandler(htmlView)
    val showAnnouncementHandler = ShowAnnouncementHandler(
        htmlView,
        operationHolder.fetchAnnouncementOperation,
        operationHolder.fetchUserViaUsernameOperation
    )
    val showAnnouncementsListHandler = ShowAnnouncementsListHandler(
        currentUserLens,
        htmlView,
        operationHolder.listAnnouncementsByCategoryTitleAndCityOperation,
        operationHolder.listCategoriesOperation,
        operationHolder.listCitiesOperation
    )
    val showCityHandler = ShowCityHandler(
        htmlView,
        operationHolder.fetchCityOperation,
        operationHolder.listCategoriesByCityOperation
    )
    val showCitiesListHandler = ShowCitiesListHandler(
        htmlView,
        operationHolder.listCitiesByNameOperation
    )
    val showCityCreationFormHandler = ShowCityCreationFormHandler(
        permissionsLens,
        htmlView,
    )
    val addCityHandler = AddCityHandler(
        permissionsLens,
        htmlView,
        operationHolder.addCityOperation
    )
    val showCategoryHandler = ShowCategoryHandler(
        htmlView,
        operationHolder.fetchCategoryOperation
    )
    val showCategoriesListHandler = ShowCategoriesListHandler(
        htmlView,
        operationHolder.listCategoriesByNameOperation
    )
    val showCategoryCreationFormHandler = ShowCategoryCreationFormHandler(
        permissionsLens,
        htmlView
    )
    val addCategoryHandler = AddCategoryHandler(
        permissionsLens,
        htmlView,
        operationHolder.addCategoryOperation
    )
    val showSpecialistHandler = ShowSpecialistHandler(
        permissionsLens,
        htmlView,
        operationHolder.fetchSpecialistOperation,
        operationHolder.listAnnouncementsBySpecialistOperation
    )
    val showSpecialistsListHandler = ShowSpecialistsListHandler(
        permissionsLens,
        htmlView,
        operationHolder.listSpecialistsByNameAndPhoneOperation
    )
    val showLoginFormHandler = ShowLoginFormHandler(
        currentUserLens,
        htmlView
    )
    val authenticateUser = AuthenticateUser(
        currentUserLens,
        htmlView,
        operationHolder.authenticateUserViaLoginOperation,
        jwtTools
    )
    val logOutUser = LogOutUser()
    val showUserCreationFormHandler = ShowUserCreationFormHandler(
        currentUserLens,
        htmlView,
        operationHolder.listCitiesOperation,
    )
    val addUserHandler = AddUserHandler(
        currentUserLens,
        htmlView,
        operationHolder.listCitiesOperation,
        operationHolder.addUserOperation,
        jwtTools
    )
    val showProfileHandler = ShowProfileHandler(
        currentUserLens,
        htmlView,
    )
    val showUserEditingFormHandler = ShowUserEditingFormHandler(
        permissionsLens,
        htmlView,
        operationHolder.listCitiesOperation
    )
    val editUserHandler = EditUserHandler(
        currentUserLens,
        permissionsLens,
        htmlView,
        operationHolder.listCitiesOperation,
        operationHolder.editUserOperation
    )
    val showRequestHandler = ShowRequestHandler(
        currentUserLens,
        permissionsLens,
        htmlView,
        operationHolder.fetchRequestInfoOperation
    )
    val showRequestsListHandler = ShowRequestsListHandler(
        currentUserLens,
        permissionsLens,
        htmlView,
        operationHolder.listRequestsByStatusOperation
    )
    val showRequestCreationFormHandler = ShowRequestCreationFormHandler(
        currentUserLens,
        permissionsLens,
        htmlView,
        operationHolder.listCategoriesForRequestOperation
    )
    val addRequestHandler = AddRequestHandler(
        currentUserLens,
        permissionsLens,
        htmlView,
        operationHolder.listCategoriesForRequestOperation,
        operationHolder.addRequestOperation
    )
    val acceptRequestHandler = AcceptRequestHandler(
        currentUserLens,
        permissionsLens,
        operationHolder.fetchRequestInfoOperation,
        operationHolder.acceptRequestOperation,
        operationHolder.fetchSpecialistOperation,
        operationHolder.addSpecialistOperation,
    )
    val showRequestDiscardingFormHandler = ShowRequestDiscardingFormHandler(
        permissionsLens,
        htmlView
    )
    val discardRequestHandler = DiscardRequestHandler(
        permissionsLens,
        htmlView,
        operationHolder.discardRequestOperation
    )
    val showCategoriesForAnnouncementFormHandler = ShowCategoriesForAnnouncementFormHandler(
        currentUserLens,
        permissionsLens,
        htmlView,
        operationHolder.listCategoriesForSpecialistOperation
    )
    val selectCategoryHandler = SelectCategoryHandler(
        currentUserLens,
        permissionsLens,
        htmlView,
        operationHolder.listCategoriesForSpecialistOperation
    )
    val showAnnouncementCreationFormHandler = ShowAnnouncementCreationFormHandler(
        currentUserLens,
        permissionsLens,
        htmlView,
        operationHolder.listCitiesOperation,
        operationHolder.fetchCategoryForSpecialistOperation,
        operationHolder.fetchDescriptionFromRequestOperation
    )
    val addAnnouncementHandler = AddAnnouncementHandler(
        currentUserLens,
        permissionsLens,
        htmlView,
        operationHolder.listCitiesOperation,
        operationHolder.fetchCategoryForSpecialistOperation,
        operationHolder.fetchDescriptionFromRequestOperation,
        operationHolder.addAnnouncementOperation
    )
    val showAnnouncementEditingFormHandler = ShowAnnouncementEditingFormHandler(
        currentUserLens,
        permissionsLens,
        htmlView,
        operationHolder.fetchAnnouncementOperation,
        operationHolder.listCategoriesForSpecialistOperation,
        operationHolder.listCitiesOperation
    )
    val editAnnouncementHandler = EditAnnouncementHandler(
        currentUserLens,
        permissionsLens,
        htmlView,
        operationHolder.fetchAnnouncementOperation,
        operationHolder.listCategoriesForSpecialistOperation,
        operationHolder.listCitiesOperation,
        operationHolder.editAnnouncementOperation
    )
    val showSpecialistEditingFormHandler = ShowSpecialistEditingFormHandler(
        currentUserLens,
        permissionsLens,
        htmlView,
    )
    val editSpecialistHandler = EditSpecialistHandler(
        currentUserLens,
        permissionsLens,
        htmlView,
        operationHolder.editSpecialistOperation
    )
}
