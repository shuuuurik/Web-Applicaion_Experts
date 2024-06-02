package ru.ac.uniyar.web.models

import org.http4k.lens.WebForm
import org.http4k.template.ViewModel
import ru.ac.uniyar.domain.database.entities.Category

class RequestCreationFormVM(
    val categories: List<Category>,
    val education: String = "",
    val form: WebForm = WebForm()
) : ViewModel
