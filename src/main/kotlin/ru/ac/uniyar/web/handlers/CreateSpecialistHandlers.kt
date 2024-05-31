package ru.ac.uniyar.web.handlers

import org.http4k.core.Body
import org.http4k.core.HttpHandler
import org.http4k.core.Request
import org.http4k.core.Response
import org.http4k.core.Status
import org.http4k.core.with
import org.http4k.lens.BiDiBodyLens
import org.http4k.lens.FormField
import org.http4k.lens.Validator
import org.http4k.lens.nonEmptyString
import org.http4k.lens.webForm
import org.http4k.template.ViewModel
import ru.ac.uniyar.domain.operations.AddSpecialistOperation
import ru.ac.uniyar.web.models.SpecialistCreationFormVM

class ShowSpecialistCreationFormHandler(
    private val htmlView: BiDiBodyLens<ViewModel>
) : HttpHandler {
    override fun invoke(request: Request): Response {
        return Response(Status.OK).with(htmlView of SpecialistCreationFormVM())
    }
}

class AddSpecialistHandler(
    private val htmlView: BiDiBodyLens<ViewModel>,
    private val addSpecialistOperation: AddSpecialistOperation
) : HttpHandler {
    companion object {
        private val fullNameFormLens = FormField.nonEmptyString().required("full_name")
        private val educationFormLens = FormField.nonEmptyString().required("education")
        private val phoneFormLens = FormField.nonEmptyString().required("phone")
        private val specialistObjectFormLens = Body.webForm(
            Validator.Feedback,
            fullNameFormLens,
            educationFormLens,
            phoneFormLens
        ).toLens()
    }
    override fun invoke(request: Request): Response {
        val webForm = specialistObjectFormLens(request)
        return if (webForm.errors.isEmpty()) {
            addSpecialistOperation.add(
                fullNameFormLens(webForm),
                educationFormLens(webForm),
                phoneFormLens(webForm),
            )
            Response(Status.FOUND).header("Location", "/specialists")
        } else {
            Response(Status.OK).with(htmlView of SpecialistCreationFormVM(webForm))
        }
    }
}
