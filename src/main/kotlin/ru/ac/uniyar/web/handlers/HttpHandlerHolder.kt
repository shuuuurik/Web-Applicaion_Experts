package ru.ac.uniyar.web.handlers

import org.http4k.lens.BiDiBodyLens
import org.http4k.template.ViewModel
import ru.ac.uniyar.domain.operations.OperationHolder

class HttpHandlerHolder(
    htmlView: BiDiBodyLens<ViewModel>,
    operationHolder: OperationHolder
) {
    val showHomePageHandler = ShowHomePageHandler(
        htmlView,
        operationHolder.countSpecialistsOperation,
        operationHolder.countAnnouncementsOperation
    )
    val redirectToHomeHandler = RedirectToHomeHandler()
    val showERMHandler = ShowERMHandler(htmlView)
    val showAnnouncementHandler = ShowAnnouncementHandler(
        htmlView,
        operationHolder.fetchAnnouncementOperation,
        operationHolder.fetchSpecialistOperation
    )
    val showAnnouncementsListHandler = ShowAnnouncementsListHandler(
        htmlView,
        operationHolder.listAnnouncementsByCategoryTitleAndCityOperation,
        operationHolder.listCategoriesOperation,
        operationHolder.listCitiesOperation
    )
    val showAnnouncementCreationFormHandler = ShowAnnouncementCreationFormHandler(
        htmlView,
        operationHolder.listCategoriesOperation,
        operationHolder.listCitiesOperation,
        operationHolder.listSpecialistsOperation
    )
    val addAnnouncementHandler = AddAnnouncementHandler(
        htmlView,
        operationHolder.listCategoriesOperation,
        operationHolder.listCitiesOperation,
        operationHolder.listSpecialistsOperation,
        operationHolder.addAnnouncementOperation
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
        htmlView,
    )
    val addCityHandler = AddCityHandler(
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
        htmlView
    )
    val addCategoryHandler = AddCategoryHandler(
        htmlView,
        operationHolder.addCategoryOperation
    )
    val showSpecialistHandler = ShowSpecialistHandler(
        htmlView,
        operationHolder.fetchSpecialistOperation,
        operationHolder.listAnnouncementsBySpecialistOperation
    )
    val showSpecialistsListHandler = ShowSpecialistsListHandler(
        htmlView,
        operationHolder.listSpecialistsByNameAndPhoneOperation
    )
    val showSpecialistCreationFormHandler = ShowSpecialistCreationFormHandler(
        htmlView
    )
    val addSpecialistHandler = AddSpecialistHandler(
        htmlView,
        operationHolder.addSpecialistOperation
    )
    val showAnnouncementForSpecialistCreationFormHandler = ShowAnnouncementForSpecialistCreationFormHandler(
        htmlView,
        operationHolder.fetchSpecialistOperation,
        operationHolder.listCategoriesOperation,
        operationHolder.listCitiesOperation
    )
    val addAnnouncementForSpecialistHandler = AddAnnouncementForSpecialistHandler(
        htmlView,
        operationHolder.fetchSpecialistOperation,
        operationHolder.listCategoriesOperation,
        operationHolder.listCitiesOperation,
        operationHolder.addAnnouncementOperation
    )
}
