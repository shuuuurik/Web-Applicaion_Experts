package ru.ac.uniyar.models

import org.http4k.template.ViewModel
import ru.ac.uniyar.domain.Announcement

class AnnouncementViewModel(val announcement: Announcement): ViewModel {
}