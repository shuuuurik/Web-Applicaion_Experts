package ru.ac.uniyar.web.models

import org.http4k.template.ViewModel
import ru.ac.uniyar.domain.database.entities.Announcement
import ru.ac.uniyar.domain.database.entities.UriWithPageQuery
import ru.ac.uniyar.domain.database.entities.User

data class SpecialistVM(
    val specialist: User,
    val announcements: List<Announcement>,
    val uri: UriWithPageQuery
) : ViewModel
