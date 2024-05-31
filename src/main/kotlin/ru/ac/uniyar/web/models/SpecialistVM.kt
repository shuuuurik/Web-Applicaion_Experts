package ru.ac.uniyar.web.models

import org.http4k.template.ViewModel
import ru.ac.uniyar.domain.database.entities.Announcement
import ru.ac.uniyar.domain.database.entities.Specialist
import ru.ac.uniyar.domain.operations.UriWithPageQuery

data class SpecialistVM(
    val specialist: Specialist,
    val announcements: List<Announcement>,
    val uri: UriWithPageQuery
) : ViewModel
