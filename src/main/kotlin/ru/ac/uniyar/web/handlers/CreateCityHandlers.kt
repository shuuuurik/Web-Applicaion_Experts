package ru.ac.uniyar.web.handlers

import org.http4k.core.Body
import org.http4k.core.HttpHandler
import org.http4k.core.Request
import org.http4k.core.Response
import org.http4k.core.Status
import org.http4k.core.with
import org.http4k.lens.FormField
import org.http4k.lens.Invalid
import org.http4k.lens.RequestContextLens
import org.http4k.lens.Validator
import org.http4k.lens.nonEmptyString
import org.http4k.lens.webForm
import ru.ac.uniyar.domain.database.entities.RolePermissions
import ru.ac.uniyar.domain.operations.AddCityOperation
import ru.ac.uniyar.web.models.CityCreationFormVM
import ru.ac.uniyar.web.templates.ContextAwareViewRender
import java.sql.SQLIntegrityConstraintViolationException

class ShowCityCreationFormHandler(
    private val permissionsLens: RequestContextLens<RolePermissions>,
    private val htmlView: ContextAwareViewRender
) : HttpHandler {
    override fun invoke(request: Request): Response {
        val permissions = permissionsLens(request)
        if (!permissions.addCity) {
            return Response(Status.UNAUTHORIZED)
        }
        return Response(Status.OK).with(htmlView(request) of CityCreationFormVM())
    }
}

class AddCityHandler(
    private val permissionsLens: RequestContextLens<RolePermissions>,
    private val htmlView: ContextAwareViewRender,
    private val addCityOperation: AddCityOperation
) : HttpHandler {
    companion object {
        private val nameFormLens = FormField.nonEmptyString().required("name")
        private val cityFormLens = Body.webForm(
            Validator.Feedback,
            nameFormLens,
        ).toLens()
    }

    override fun invoke(request: Request): Response {
        val permissions = permissionsLens(request)
        if (!permissions.addCity) {
            return Response(Status.UNAUTHORIZED)
        }
        var webForm = cityFormLens(request)
        return if (webForm.errors.isEmpty()) {
            try {
                addCityOperation.add(nameFormLens(webForm))
                Response(Status.FOUND).header("Location", "/cities")
            } catch (_: SQLIntegrityConstraintViolationException) {
                val newErrors = webForm.errors +
                    Invalid(nameFormLens.meta.copy(description = "Такой город уже существует"))
                webForm = webForm.copy(errors = newErrors)
                Response(Status.OK).with(htmlView(request) of CityCreationFormVM(webForm))
            }
        } else {
            Response(Status.OK).with(htmlView(request) of CityCreationFormVM(webForm))
        }
    }
}
