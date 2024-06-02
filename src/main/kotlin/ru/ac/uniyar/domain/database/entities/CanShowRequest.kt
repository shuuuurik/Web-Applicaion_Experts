package ru.ac.uniyar.domain.database.entities

fun canShowRequest(user: User?, request: Request) = user != null && request.username == user.username
