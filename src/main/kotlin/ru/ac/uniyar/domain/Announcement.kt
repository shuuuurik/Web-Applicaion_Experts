package ru.ac.uniyar.domain

import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

data class Announcement (var serviceCategory: String, var title: String, var description: String, var specialist: String,
                    var education: String, val phoneNumber: String) {
    val additionTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd.MM.yy, HH:mm"))
}