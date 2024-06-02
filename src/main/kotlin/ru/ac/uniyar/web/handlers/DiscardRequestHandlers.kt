package ru.ac.uniyar.web.handlers

import org.http4k.core.Body
import org.http4k.core.HttpHandler
import org.http4k.core.Request
import org.http4k.core.Response
import org.http4k.core.Status
import org.http4k.core.with
import org.http4k.lens.FormField
import org.http4k.lens.Path
import org.http4k.lens.RequestContextLens
import org.http4k.lens.Validator
import org.http4k.lens.int
import org.http4k.lens.nonEmptyString
import org.http4k.lens.webForm
import ru.ac.uniyar.domain.database.entities.RolePermissions
import ru.ac.uniyar.domain.operations.DiscardRequestOperation
import ru.ac.uniyar.web.lenses.lensOrNull
import ru.ac.uniyar.web.models.RequestDiscardingFormVM
import ru.ac.uniyar.web.templates.ContextAwareViewRender

class ShowRequestDiscardingFormHandler(
    private val permissionsLens: RequestContextLens<RolePermissions>,
    private val htmlView: ContextAwareViewRender,
) : HttpHandler {
    override fun invoke(request: Request): Response {
        val permissions = permissionsLens(request)
        if (!permissions.editRequest)
            return Response(Status.UNAUTHORIZED)
        return Response(Status.OK).with(htmlView(request) of RequestDiscardingFormVM())
    }
}

class DiscardRequestHandler(
    private val permissionsLens: RequestContextLens<RolePermissions>,
    private val htmlView: ContextAwareViewRender,
    private val discardRequestOperation: DiscardRequestOperation
) : HttpHandler {
    companion object {
        private val indexLens = Path.int().of("index")
        private val reasonFormLens = FormField.nonEmptyString().required("reason")
        private val requestFormLens = Body.webForm(
            Validator.Feedback,
            reasonFormLens,
        ).toLens()
    }
    @Suppress("ReturnCount")
    override fun invoke(request: Request): Response {
        val permissions = permissionsLens(request)
        if (!permissions.editRequest)
            return Response(Status.UNAUTHORIZED)
        val requestId = lensOrNull(indexLens, request) ?: return Response(Status.BAD_REQUEST)
        val webForm = requestFormLens(request)
        return if (webForm.errors.isEmpty()) {
            discardRequestOperation.discard(requestId, reasonFormLens(webForm))
            Response(Status.FOUND).header("Location", "/requests/$requestId")
        } else {
            Response(Status.OK).with(htmlView(request) of RequestDiscardingFormVM(webForm))
        }
    }
}
