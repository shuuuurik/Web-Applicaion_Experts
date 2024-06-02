package ru.ac.uniyar.web.models

import org.http4k.lens.WebForm
import org.http4k.template.ViewModel
import ru.ac.uniyar.domain.database.entities.City

data class UserCreationFormVM(
    val cities: List<City>,
    val form: WebForm = WebForm()
) : ViewModel
