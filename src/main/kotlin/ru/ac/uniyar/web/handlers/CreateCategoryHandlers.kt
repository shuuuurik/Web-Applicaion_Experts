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
import ru.ac.uniyar.domain.operations.AddCategoryOperation
import ru.ac.uniyar.web.models.CategoryCreationFormVM
import ru.ac.uniyar.web.templates.ContextAwareViewRender
import java.sql.SQLIntegrityConstraintViolationException

class ShowCategoryCreationFormHandler(
    private val permissionsLens: RequestContextLens<RolePermissions>,
    private val htmlView: ContextAwareViewRender
) : HttpHandler {
    override fun invoke(request: Request): Response {
        val permissions = permissionsLens(request)
        if (!permissions.addCategory)
            return Response(Status.UNAUTHORIZED)
        return Response(Status.OK).with(htmlView(request) of CategoryCreationFormVM())
    }
}

class AddCategoryHandler(
    private val permissionsLens: RequestContextLens<RolePermissions>,
    private val htmlView: ContextAwareViewRender,
    private val addCategoryOperation: AddCategoryOperation
) : HttpHandler {
    companion object {
        private val nameFormLens = FormField.nonEmptyString().required("name")
        private val categoryFormLens = Body.webForm(
            Validator.Feedback,
            nameFormLens,
        ).toLens()
    }
    override fun invoke(request: Request): Response {
        val permissions = permissionsLens(request)
        if (!permissions.addCategory)
            return Response(Status.UNAUTHORIZED)
        var webForm = categoryFormLens(request)
        return if (webForm.errors.isEmpty()) {
            try {
                addCategoryOperation.add(nameFormLens(webForm))
                Response(Status.FOUND).header("Location", "/categories")
            } catch (_: SQLIntegrityConstraintViolationException) {
                val newErrors = webForm.errors +
                    Invalid(nameFormLens.meta.copy(description = "Такая категория услуг уже существует"))
                webForm = webForm.copy(errors = newErrors)
                Response(Status.OK).with(htmlView(request) of CategoryCreationFormVM(webForm))
            }
        } else {
            Response(Status.OK).with(htmlView(request) of CategoryCreationFormVM(webForm))
        }
    }
}
