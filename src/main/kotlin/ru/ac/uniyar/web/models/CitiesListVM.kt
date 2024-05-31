package ru.ac.uniyar.web.models

import org.http4k.template.ViewModel
import ru.ac.uniyar.domain.database.entities.City
import ru.ac.uniyar.domain.operations.UriWithPageQuery

data class CitiesListVM(
    val cities: List<City>,
    val uri: UriWithPageQuery,
    val name: String,
) : ViewModel
