package ru.ac.uniyar.domain.operations

import org.ktorm.database.Database

class OperationHolder(database: Database) {
    val listAnnouncementsOperation = ListAnnouncementsOperation(database)
    val fetchAnnouncementOperation = FetchAnnouncementOperation(database)
    val addAnnouncementOperation = AddAnnouncementOperation(database)
    val listAnnouncementsByCategoryTitleAndCityOperation = ListAnnouncementsByCategoryTitleAndCityOperation(database)
    val listAnnouncementsBySpecialistOperation = ListAnnouncementsBySpecialistOperation(database)
    val countAnnouncementsOperation = CountAnnouncementsOperation(database)

    val listCitiesOperation = ListCitiesOperation(database)
    val fetchCityOperation = FetchCityOperation(database)
    val addCityOperation = AddCityOperation(database)
    val listCitiesByNameOperation = ListCitiesByNameOperation(database)

    val listCategoriesOperation = ListCategoriesOperation(database)
    val fetchCategoryOperation = FetchCategoryOperation(database)
    val addCategoryOperation = AddCategoryOperation(database)
    val listCategoriesByCityOperation = ListCategoriesByCityOperation(database)
    val listCategoriesByNameOperation = ListCategoriesByNameOperation(database)

    val listSpecialistsOperation = ListSpecialistsOperation(database)
    val fetchSpecialistOperation = FetchSpecialistOperation(database)
    val addSpecialistOperation = AddSpecialistOperation(database)
    val listSpecialistsByNameAndPhoneOperation = ListSpecialistsByNameAndPhoneOperation(database)
    val countSpecialistsOperation = CountSpecialistsOperation(database)
}
