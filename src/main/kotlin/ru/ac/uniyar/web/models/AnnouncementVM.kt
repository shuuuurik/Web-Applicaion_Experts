package ru.ac.uniyar.web.models

import org.http4k.template.ViewModel
import ru.ac.uniyar.domain.database.entities.AnnouncementWithCategoryId

data class AnnouncementVM(
    val announcement: AnnouncementWithCategoryId,
    val specialistName: String
) : ViewModel
