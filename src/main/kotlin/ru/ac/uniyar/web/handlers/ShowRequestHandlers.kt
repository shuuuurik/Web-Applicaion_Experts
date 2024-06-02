package ru.ac.uniyar.web.handlers

import org.http4k.core.HttpHandler
import org.http4k.core.Request
import org.http4k.core.Response
import org.http4k.core.Status
import org.http4k.core.with
import org.http4k.lens.Path
import org.http4k.lens.RequestContextLens
import org.http4k.lens.int
import ru.ac.uniyar.domain.database.entities.RolePermissions
import ru.ac.uniyar.domain.database.entities.User
import ru.ac.uniyar.domain.operations.AcceptRequestOperation
import ru.ac.uniyar.domain.operations.AddSpecialistOperation
import ru.ac.uniyar.domain.operations.FetchRequestInfoOperation
import ru.ac.uniyar.domain.operations.FetchSpecialistOperation
import ru.ac.uniyar.domain.operations.RequestFetchError
import ru.ac.uniyar.web.lenses.lensOrNull
import ru.ac.uniyar.web.models.RequestVM
import ru.ac.uniyar.web.templates.ContextAwareViewRender

class ShowRequestHandler(
    private val currentUserLens: RequestContextLens<User?>,
    private val permissionsLens: RequestContextLens<RolePermissions>,
    private val htmlView: ContextAwareViewRender,
    private val fetchRequestInfoOperation: FetchRequestInfoOperation,
) : HttpHandler {
    companion object {
        private val indexLens = Path.int().of("index")
    }

    @Suppress("ReturnCount")
    override fun invoke(request: Request): Response {
        val permissions = permissionsLens(request)
        val requestId = lensOrNull(indexLens, request) ?: return Response(Status.BAD_REQUEST)
        val currentUser = currentUserLens(request)
        return try {
            val requestInfo = fetchRequestInfoOperation.fetch(currentUser, requestId)
            if (requestInfo.showDetails || permissions.showRequest) {
                Response(Status.OK).with(htmlView(request) of RequestVM(requestInfo))
            } else {
                Response(Status.UNAUTHORIZED)
            }
        } catch (_: RequestFetchError) {
            Response(Status.BAD_REQUEST)
        }
    }
}
@Suppress("LongParameterList")
class AcceptRequestHandler(
    private val currentUserLens: RequestContextLens<User?>,
    private val permissionsLens: RequestContextLens<RolePermissions>,
    private val fetchRequestInfoOperation: FetchRequestInfoOperation,
    private val acceptRequestOperation: AcceptRequestOperation,
    private val fetchSpecialistOperation: FetchSpecialistOperation,
    private val addSpecialistOperation: AddSpecialistOperation,
) : HttpHandler {
    companion object {
        private val indexLens = Path.int().of("index")
    }
    @Suppress("ReturnCount")
    override fun invoke(request: Request): Response {
        val permissions = permissionsLens(request)
        if (!permissions.editRequest)
            return Response(Status.UNAUTHORIZED)
        val requestId = lensOrNull(indexLens, request) ?: return Response(Status.BAD_REQUEST)
        val currentUser = currentUserLens(request)!!
        return try {
            val requestInfo = fetchRequestInfoOperation.fetch(currentUser, requestId)
            acceptRequestOperation.accept(requestInfo.request.id)
            if (fetchSpecialistOperation.fetch(requestInfo.user.username) == null) {
                addSpecialistOperation.add(
                    requestInfo.user.username,
                    requestInfo.request.education,
                )
            }
            Response(Status.FOUND).header("Location", "/requests")
        } catch (_: RequestFetchError) {
            Response(Status.BAD_REQUEST)
        }
    }
}
