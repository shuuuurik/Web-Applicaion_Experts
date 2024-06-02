package ru.ac.uniyar.domain.database.entities

import org.ktorm.dsl.QueryRowSet

class RequestInfo(
    val user: User,
    val request: Request,
    val showDetails: Boolean
) {
    companion object {
        fun fromResultSet(row: QueryRowSet, user: User?): RequestInfo? =
            try {
                val request = Request.fromResultSet(row)!!
                RequestInfo(
                    User.fromResultSet(row)!!,
                    request,
                    canShowRequest(user, request)
                )
            } catch (_: NullPointerException) {
                null
            }
    }
}
