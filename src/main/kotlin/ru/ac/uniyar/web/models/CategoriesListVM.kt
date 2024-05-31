package ru.ac.uniyar.web.models

import org.http4k.template.ViewModel
import ru.ac.uniyar.domain.database.entities.CategoryWithAnnouncementsNumber
import ru.ac.uniyar.domain.operations.UriWithPageQuery

data class CategoriesListVM(
    val categories: List<CategoryWithAnnouncementsNumber>,
    val uri: UriWithPageQuery,
    val name: String,
) : ViewModel
