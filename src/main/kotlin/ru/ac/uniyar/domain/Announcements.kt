package ru.ac.uniyar.domain

class Announcements {
    val announcements = mutableListOf<Announcement>()
    fun add(announcement: Announcement){
        announcements.add(announcement)
    }
    fun fetchAllAnnouncements(): Iterable<IndexedValue<Announcement>> = announcements.withIndex()
}