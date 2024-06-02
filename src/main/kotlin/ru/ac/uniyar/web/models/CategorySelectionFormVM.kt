package ru.ac.uniyar.web.models

import org.http4k.lens.WebForm
import org.http4k.template.ViewModel
import ru.ac.uniyar.domain.database.entities.Category

class CategorySelectionFormVM(
    val categories: List<Category>,
    val form: WebForm = WebForm()
) : ViewModel
