package ru.ac.uniyar.domain.database.tables

import org.ktorm.dsl.count

val announcementCount = count(AnnouncementTable.id).aliased("announcement_count")
