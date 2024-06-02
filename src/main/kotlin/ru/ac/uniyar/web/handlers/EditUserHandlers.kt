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
import org.http4k.lens.nonEmptyString
import org.http4k.lens.webForm
import ru.ac.uniyar.domain.database.entities.RolePermissions
import ru.ac.uniyar.domain.database.entities.User
import ru.ac.uniyar.domain.operations.EditUserOperation
import ru.ac.uniyar.domain.operations.ListCitiesOperation
import ru.ac.uniyar.web.models.UserEditingFormVM
import ru.ac.uniyar.web.templates.ContextAwareViewRender

class ShowUserEditingFormHandler(
    private val permissionsLens: RequestContextLens<RolePermissions>,
    private val htmlView: ContextAwareViewRender,
    private val listCitiesOperation: ListCitiesOperation,
) : HttpHandler {
    override fun invoke(request: Request): Response {
        val permissions = permissionsLens(request)
        if (!permissions.editUser)
            return Response(Status.UNAUTHORIZED)
        return Response(Status.OK).with(
            htmlView(request) of UserEditingFormVM(
                listCitiesOperation.list()
            )
        )
    }
}

class EditUserHandler(
    private val currentUserLens: RequestContextLens<User?>,
    private val permissionsLens: RequestContextLens<RolePermissions>,
    private val htmlView: ContextAwareViewRender,
    private val listCitiesOperation: ListCitiesOperation,
    private val editUserOperation: EditUserOperation
) : HttpHandler {
    companion object {
        private val fullNameFormLens = FormField.nonEmptyString().required("full_name")
        private val phoneFormLens = FormField.nonEmptyString().required("phone")
        private val cityFormLens = FormField.nonEmptyString().required("city")
        private val userFormLens = Body.webForm(
            Validator.Feedback,
            fullNameFormLens,
            phoneFormLens,
            cityFormLens,
        ).toLens()
    }
    override fun invoke(request: Request): Response {
        val permissions = permissionsLens(request)
        if (!permissions.editUser)
            return Response(Status.UNAUTHORIZED)
        val webForm = userFormLens(request)
        val user = currentUserLens(request)!!
        val cities = listCitiesOperation.list()
        return if (webForm.errors.isEmpty()) {
            editUserOperation.edit(
                user.username, fullNameFormLens(webForm), phoneFormLens(webForm), cityFormLens(webForm)
            )
            Response(Status.FOUND).header("Location", "/profile")
        } else {
            Response(Status.OK).with(htmlView(request) of UserEditingFormVM(cities, webForm))
        }
    }
}
