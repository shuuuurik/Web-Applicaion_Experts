package ru.ac.uniyar.web.models

import org.http4k.lens.WebForm
import org.http4k.template.ViewModel
import ru.ac.uniyar.domain.database.entities.Category
import ru.ac.uniyar.domain.database.entities.City

data class AnnouncementForSpecialistCreationFormVM(
    val categories: List<Category>,
    val cities: List<City>,
    val specialistName: String,
    val form: WebForm = WebForm()
) : ViewModel
