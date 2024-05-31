package ru.ac.uniyar.web.models

import org.http4k.template.ViewModel
import ru.ac.uniyar.domain.database.entities.Specialist
import ru.ac.uniyar.domain.operations.UriWithPageQuery

data class SpecialistsListVM(
    val specialists: List<Specialist>,
    val uri: UriWithPageQuery,
    val fullName: String,
    val phone: String,
) : ViewModel
