package ru.ac.uniyar.web.models

import org.http4k.template.ViewModel

data class HomePageVM(
    val specialistsNumber: Int,
    val avgAnnouncementNumber: Float
) : ViewModel
