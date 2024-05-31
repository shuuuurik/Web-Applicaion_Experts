package ru.ac.uniyar.web.models

import org.http4k.lens.WebForm
import org.http4k.template.ViewModel
import ru.ac.uniyar.domain.database.entities.Category
import ru.ac.uniyar.domain.database.entities.City
import ru.ac.uniyar.domain.database.entities.Specialist

data class AnnouncementCreationFormVM(
    val categories: List<Category>,
    val cities: List<City>,
    val specialists: List<Specialist>,
    val form: WebForm = WebForm()
) : ViewModel
