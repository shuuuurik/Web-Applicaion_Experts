package ru.ac.uniyar.web.models

import org.http4k.template.ViewModel
import ru.ac.uniyar.domain.database.entities.User

data class HomePageVM(
    val specialistsNumber: Int,
    val avgAnnouncementNumber: Float,
    val users: List<User>
) : ViewModel
