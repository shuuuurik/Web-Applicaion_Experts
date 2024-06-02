package ru.ac.uniyar.domain.operations

import org.ktorm.database.Database

class OperationHolder(database: Database, salt: String) {
    val fetchAnnouncementOperation = FetchAnnouncementOperation(database)
    val addAnnouncementOperation = AddAnnouncementOperation(database)
    val listAnnouncementsByCategoryTitleAndCityOperation = ListAnnouncementsByCategoryTitleAndCityOperation(database)
    val listAnnouncementsBySpecialistOperation = ListAnnouncementsBySpecialistOperation(database)
    val countAnnouncementsOperation = CountAnnouncementsOperation(database)
    val editAnnouncementOperation = EditAnnouncementOperation(database)

    val listCitiesOperation = ListCitiesOperation(database)
    val fetchCityOperation = FetchCityOperation(database)
    val addCityOperation = AddCityOperation(database)
    val listCitiesByNameOperation = ListCitiesByNameOperation(database)

    val listCategoriesOperation = ListCategoriesOperation(database)
    val fetchCategoryOperation = FetchCategoryOperation(database)
    val addCategoryOperation = AddCategoryOperation(database)
    val listCategoriesByCityOperation = ListCategoriesByCityOperation(database)
    val listCategoriesByNameOperation = ListCategoriesByNameOperation(database)
    val listCategoriesForRequestOperation = ListCategoriesForRequestOperation(database)

    val addSpecialistOperation = AddSpecialistOperation(database)
    val listSpecialistsByNameAndPhoneOperation = ListSpecialistsByNameAndPhoneOperation(database)
    val countSpecialistsOperation = CountSpecialistsOperation(database)
    val fetchSpecialistOperation = FetchSpecialistOperation(database)
    val editSpecialistOperation = EditSpecialistOperation(database)

    val authenticateUserViaLoginOperation = AuthenticateUserViaLoginOperation(database, salt)
    val fetchUserViaUsernameOperation = FetchUserViaUsernameOperation(database)
    val fetchRoleViaIdOperation = FetchRoleViaIdOperation(database)
    val addUserOperation = AddUserOperation(database, salt)
    val editUserOperation = EditUserOperation(database)
    val listUsersOperation = ListUsersOperation(database)

    val listRequestsByStatusOperation = ListRequestsByStatusOperation(database)
    val addRequestOperation = AddRequestOperation(database)
    val fetchRequestInfoOperation = FetchRequestInfoOperation(database)
    val acceptRequestOperation = AcceptRequestOperation(database)
    val discardRequestOperation = DiscardRequestOperation(database)
    val listCategoriesForSpecialistOperation = ListCategoriesForSpecialistOperation(database)
    val fetchCategoryForSpecialistOperation = FetchCategoryForSpecialistOperation(database)
    val fetchDescriptionFromRequestOperation = FetchDescriptionFromRequestOperation(database)
}
