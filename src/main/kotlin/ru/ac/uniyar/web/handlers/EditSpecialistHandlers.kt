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
import org.http4k.lens.nonEmptyString
import org.http4k.lens.string
import org.http4k.lens.webForm
import ru.ac.uniyar.domain.database.entities.RolePermissions
import ru.ac.uniyar.domain.database.entities.User
import ru.ac.uniyar.domain.operations.EditSpecialistOperation
import ru.ac.uniyar.web.lenses.lensOrNull
import ru.ac.uniyar.web.models.SpecialistEditingFormVM
import ru.ac.uniyar.web.templates.ContextAwareViewRender

class ShowSpecialistEditingFormHandler(
    private val currentUserLens: RequestContextLens<User?>,
    private val permissionsLens: RequestContextLens<RolePermissions>,
    private val htmlView: ContextAwareViewRender,
) : HttpHandler {
    companion object {
        private val loginLens = Path.string().of("login")
    }

    @Suppress("ReturnCount")
    override fun invoke(request: Request): Response {
        val permissions = permissionsLens(request)
        if (permissions.name != "Специалист")
            return Response(Status.UNAUTHORIZED)
        val specialistLogin = lensOrNull(loginLens, request) ?: return Response(Status.BAD_REQUEST)
        val user = currentUserLens(request)!!
        if (specialistLogin != user.username) {
            return Response(Status.BAD_REQUEST)
        }
        return Response(Status.OK).with(htmlView(request) of SpecialistEditingFormVM())
    }
}

class EditSpecialistHandler(
    private val currentUserLens: RequestContextLens<User?>,
    private val permissionsLens: RequestContextLens<RolePermissions>,
    private val htmlView: ContextAwareViewRender,
    private val editSpecialistOperation: EditSpecialistOperation
) : HttpHandler {
    companion object {
        private val loginLens = Path.string().of("login")
        private val educationFormLens = FormField.nonEmptyString().required("education")
        private val specialistFormLens = Body.webForm(
            Validator.Feedback,
            educationFormLens
        ).toLens()
    }

    @Suppress("ReturnCount")
    override fun invoke(request: Request): Response {
        val permissions = permissionsLens(request)
        if (permissions.name != "Специалист")
            return Response(Status.UNAUTHORIZED)
        val specialistLogin = lensOrNull(loginLens, request) ?: return Response(Status.BAD_REQUEST)
        val user = currentUserLens(request)!!
        if (specialistLogin != user.username) {
            return Response(Status.BAD_REQUEST)
        }
        val webForm = specialistFormLens(request)
        return if (webForm.errors.isEmpty()) {
            editSpecialistOperation.edit(
                user.username, educationFormLens(webForm)
            )
            Response(Status.FOUND).header("Location", "/specialists/${user.username}")
        } else {
            Response(Status.OK).with(htmlView(request) of SpecialistEditingFormVM(webForm))
        }
    }
}
