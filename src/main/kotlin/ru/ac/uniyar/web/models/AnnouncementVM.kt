package ru.ac.uniyar.web.models

import org.http4k.template.ViewModel
import ru.ac.uniyar.domain.database.entities.Announcement

data class AnnouncementVM(val announcement: Announcement, val specialistName: String) : ViewModel
