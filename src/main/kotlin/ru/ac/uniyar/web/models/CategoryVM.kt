package ru.ac.uniyar.web.models

import org.http4k.template.ViewModel
import ru.ac.uniyar.domain.database.entities.CategoryWithAnnouncementsNumber

data class CategoryVM(
    val category: CategoryWithAnnouncementsNumber
) : ViewModel
