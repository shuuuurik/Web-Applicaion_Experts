package ru.ac.uniyar.web.models

import org.http4k.lens.WebForm
import org.http4k.template.ViewModel
import ru.ac.uniyar.domain.database.entities.City

data class AnnouncementCreationFormVM(
    val categoryName: String,
    val cities: List<City>,
    val specialistName: String,
    val description: String,
    val form: WebForm = WebForm()
) : ViewModel
