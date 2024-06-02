package ru.ac.uniyar.web.filters

import org.http4k.core.Filter
import org.http4k.core.HttpHandler
import org.http4k.core.Request
import org.http4k.core.with
import org.http4k.lens.RequestContextLens
import ru.ac.uniyar.domain.database.entities.RolePermissions
import ru.ac.uniyar.domain.database.entities.User
import ru.ac.uniyar.domain.operations.FetchRoleViaIdOperation

fun authorizationFilter(
    currentUser: RequestContextLens<User?>,
    permissionsLens: RequestContextLens<RolePermissions>,
    fetchRoleViaIdOperation: FetchRoleViaIdOperation,
): Filter = Filter { next: HttpHandler ->
    { request: Request ->
        val permissions = currentUser(request)?.let {
            fetchRoleViaIdOperation.fetch(it.roleId)
        } ?: RolePermissions.ANONYMOUS_ROLE
        val authorizedRequest = request.with(permissionsLens of permissions)
        next(authorizedRequest)
    }
}
