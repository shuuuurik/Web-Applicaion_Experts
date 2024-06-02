package ru.ac.uniyar.web.handlers

import org.http4k.core.Body
import org.http4k.core.HttpHandler
import org.http4k.core.Request
import org.http4k.core.Response
import org.http4k.core.Status
import org.http4k.core.with
import org.http4k.lens.FormField
import org.http4k.lens.RequestContextLens
import org.http4k.lens.Validator
import org.http4k.lens.int
import org.http4k.lens.nonEmptyString
import org.http4k.lens.webForm
import ru.ac.uniyar.domain.database.entities.RolePermissions
import ru.ac.uniyar.domain.database.entities.User
import ru.ac.uniyar.domain.operations.AddRequestOperation
import ru.ac.uniyar.domain.operations.ListCategoriesForRequestOperation
import ru.ac.uniyar.web.models.RequestCreationFormVM
import ru.ac.uniyar.web.templates.ContextAwareViewRender

class ShowRequestCreationFormHandler(
    private val currentUserLens: RequestContextLens<User?>,
    private val permissionsLens: RequestContextLens<RolePermissions>,
    private val htmlView: ContextAwareViewRender,
    private val listCategoriesForRequestOperation: ListCategoriesForRequestOperation
) : HttpHandler {
    override fun invoke(request: Request): Response {
        val permissions = permissionsLens(request)
        if (!permissions.addRequest)
            return Response(Status.UNAUTHORIZED)
        val user = currentUserLens(request)!!
        val education = user.education ?: ""
        return Response(Status.OK).with(
            htmlView(request) of RequestCreationFormVM(
                listCategoriesForRequestOperation.list(user.username),
                education = education
            )
        )
    }
}

class AddRequestHandler(
    private val currentUserLens: RequestContextLens<User?>,
    private val permissionsLens: RequestContextLens<RolePermissions>,
    private val htmlView: ContextAwareViewRender,
    private val listCategoriesForRequestOperation: ListCategoriesForRequestOperation,
    private val addRequestOperation: AddRequestOperation
) : HttpHandler {
    companion object {
        private val categoryFormLens = FormField.int().required("category")
        private val educationFormLens = FormField.nonEmptyString().required("education")
        private val experienceFormLens = FormField.nonEmptyString().required("experience")
        private val requestFormLens = Body.webForm(
            Validator.Feedback,
            categoryFormLens,
            educationFormLens,
            experienceFormLens
        ).toLens()
    }
    override fun invoke(request: Request): Response {
        val permissions = permissionsLens(request)
        if (!permissions.addRequest)
            return Response(Status.UNAUTHORIZED)
        val currentUser = currentUserLens(request)!!
        val webForm = requestFormLens(request)
        return if (webForm.errors.isEmpty()) {
            addRequestOperation.add(
                currentUser.username,
                categoryFormLens(webForm),
                educationFormLens(webForm),
                experienceFormLens(webForm),
            )
            Response(Status.FOUND).header("Location", "/requests")
        } else {
            Response(Status.OK).with(
                htmlView(request) of RequestCreationFormVM(
                    listCategoriesForRequestOperation.list(currentUser.username),
                    form = webForm
                )
            )
        }
    }
}
