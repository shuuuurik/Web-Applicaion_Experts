package ru.ac.uniyar.web.models

import org.http4k.template.ViewModel
import ru.ac.uniyar.domain.database.entities.UriWithPageQuery
import ru.ac.uniyar.domain.database.entities.User

data class SpecialistsListVM(
    val specialists: List<User>,
    val uri: UriWithPageQuery,
    val fullName: String,
    val phone: String,
) : ViewModel
