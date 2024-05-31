package ru.ac.uniyar.web.handlers

import org.http4k.core.Body
import org.http4k.core.HttpHandler
import org.http4k.core.Request
import org.http4k.core.Response
import org.http4k.core.Status
import org.http4k.core.with
import org.http4k.lens.BiDiBodyLens
import org.http4k.lens.FormField
import org.http4k.lens.Invalid
import org.http4k.lens.Validator
import org.http4k.lens.nonEmptyString
import org.http4k.lens.webForm
import org.http4k.template.ViewModel
import ru.ac.uniyar.domain.operations.AddCategoryOperation
import ru.ac.uniyar.web.models.CategoryCreationFormVM
import java.sql.SQLIntegrityConstraintViolationException

class ShowCategoryCreationFormHandler(
    private val htmlView: BiDiBodyLens<ViewModel>
) : HttpHandler {
    override fun invoke(request: Request): Response {
        return Response(Status.OK).with(htmlView of CategoryCreationFormVM())
    }
}

class AddCategoryHandler(
    private val htmlView: BiDiBodyLens<ViewModel>,
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
        var webForm = categoryFormLens(request)
        return if (webForm.errors.isEmpty()) {
            try {
                addCategoryOperation.add(nameFormLens(webForm))
                Response(Status.FOUND).header("Location", "/categories")
            } catch (_: SQLIntegrityConstraintViolationException) {
                val newErrors = webForm.errors +
                    Invalid(nameFormLens.meta.copy(description = "Такая категория услуг уже существует"))
                webForm = webForm.copy(errors = newErrors)
                Response(Status.OK).with(htmlView of CategoryCreationFormVM(webForm))
            }
        } else {
            Response(Status.OK).with(htmlView of CategoryCreationFormVM(webForm))
        }
    }
}
