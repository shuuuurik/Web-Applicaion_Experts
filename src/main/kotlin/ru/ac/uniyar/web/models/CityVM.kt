package ru.ac.uniyar.web.models

import org.http4k.template.ViewModel
import ru.ac.uniyar.domain.database.entities.CategoryWithAnnouncementsNumber
import ru.ac.uniyar.domain.database.entities.City

data class CityVM(
    val city: City,
    val categories: List<CategoryWithAnnouncementsNumber>,
) : ViewModel
