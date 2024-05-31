package ru.ac.uniyar.web.models

import org.http4k.template.ViewModel
import ru.ac.uniyar.domain.database.entities.Announcement
import ru.ac.uniyar.domain.database.entities.Category
import ru.ac.uniyar.domain.database.entities.City
import ru.ac.uniyar.domain.operations.UriWithPageQuery

data class AnnouncementsListVM(
    val announcements: List<Announcement>,
    val uri: UriWithPageQuery,
    val category: String,
    val title: String,
    val city: String,
    val categories: List<Category>,
    val cities: List<City>,
) : ViewModel
