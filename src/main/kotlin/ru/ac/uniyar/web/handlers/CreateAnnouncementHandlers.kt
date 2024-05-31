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
import org.http4k.lens.int
import org.http4k.lens.nonEmptyString
import org.http4k.lens.webForm
import org.http4k.template.ViewModel
import ru.ac.uniyar.domain.operations.AddAnnouncementOperation
import ru.ac.uniyar.domain.operations.ListCategoriesOperation
import ru.ac.uniyar.domain.operations.ListCitiesOperation
import ru.ac.uniyar.domain.operations.ListSpecialistsOperation
import ru.ac.uniyar.web.models.AnnouncementCreationFormVM

class ShowAnnouncementCreationFormHandler(
    private val htmlView: BiDiBodyLens<ViewModel>,
    private val listCategoriesOperation: ListCategoriesOperation,
    private val listCitiesOperation: ListCitiesOperation,
    private val listSpecialistsOperation: ListSpecialistsOperation
) : HttpHandler {
    override fun invoke(request: Request): Response {
        return Response(Status.OK).with(
            htmlView of AnnouncementCreationFormVM(
                listCategoriesOperation.list(),
                listCitiesOperation.list(),
                listSpecialistsOperation.list()
            )
        )
    }
}

class AddAnnouncementHandler(
    private val htmlView: BiDiBodyLens<ViewModel>,
    private val listCategoriesOperation: ListCategoriesOperation,
    private val listCitiesOperation: ListCitiesOperation,
    private val listSpecialistsOperation: ListSpecialistsOperation,
    private val addAnnouncementOperation: AddAnnouncementOperation
) : HttpHandler {
    companion object {
        private val categoryFormLens = FormField.nonEmptyString().required("category")
        private val titleFormLens = FormField.nonEmptyString().required("title")
        private val cityFormLens = FormField.nonEmptyString().required("city")
        private val descriptionFormLens = FormField.nonEmptyString().required("description")
        private val specialistFormLens = FormField.int().required("specialist")
        private val announcementFormLens = Body.webForm(
            Validator.Feedback,
            categoryFormLens,
            titleFormLens,
            cityFormLens,
            descriptionFormLens,
            specialistFormLens,
        ).toLens()
    }
    override fun invoke(request: Request): Response {
        val webForm = announcementFormLens(request)
        if (webForm.errors.isEmpty()) {
            addAnnouncementOperation.add(
                categoryFormLens(webForm),
                titleFormLens(webForm),
                cityFormLens(webForm),
                descriptionFormLens(webForm),
                specialistFormLens(webForm)
            )
            return Response(Status.FOUND).header("Location", "/announcements")
        } else {
            return Response(Status.OK).with(
                htmlView of AnnouncementCreationFormVM(
                    listCategoriesOperation.list(),
                    listCitiesOperation.list(),
                    listSpecialistsOperation.list(),
                    webForm
                )
            )
        }
    }
}
