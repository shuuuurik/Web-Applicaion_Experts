package ru.ac.uniyar.web.handlers

import org.http4k.core.Body
import org.http4k.core.HttpHandler
import org.http4k.core.Request
import org.http4k.core.Response
import org.http4k.core.Status
import org.http4k.core.with
import org.http4k.lens.BiDiBodyLens
import org.http4k.lens.FormField
import org.http4k.lens.Path
import org.http4k.lens.Validator
import org.http4k.lens.int
import org.http4k.lens.nonEmptyString
import org.http4k.lens.webForm
import org.http4k.template.ViewModel
import ru.ac.uniyar.domain.operations.AddAnnouncementOperation
import ru.ac.uniyar.domain.operations.FetchSpecialistOperation
import ru.ac.uniyar.domain.operations.ListCategoriesOperation
import ru.ac.uniyar.domain.operations.ListCitiesOperation
import ru.ac.uniyar.web.models.AnnouncementForSpecialistCreationFormVM

class ShowAnnouncementForSpecialistCreationFormHandler(
    private val htmlView: BiDiBodyLens<ViewModel>,
    private val fetchSpecialistOperation: FetchSpecialistOperation,
    private val listCategoriesOperation: ListCategoriesOperation,
    private val listCitiesOperation: ListCitiesOperation
) : HttpHandler {
    companion object {
        private val indexLens = Path.int().of("index")
    }
    override fun invoke(request: Request): Response {
        val index = indexLens(request)
        val specialist = fetchSpecialistOperation.fetch(index) ?: return Response(Status.BAD_REQUEST)
        return Response(Status.OK).with(
            htmlView of AnnouncementForSpecialistCreationFormVM(
                listCategoriesOperation.list(),
                listCitiesOperation.list(),
                specialist.fullName
            )
        )
    }
}

class AddAnnouncementForSpecialistHandler(
    private val htmlView: BiDiBodyLens<ViewModel>,
    private val fetchSpecialistOperation: FetchSpecialistOperation,
    private val listCategoriesOperation: ListCategoriesOperation,
    private val listCitiesOperation: ListCitiesOperation,
    private val addAnnouncementOperation: AddAnnouncementOperation
) : HttpHandler {
    companion object {
        private val indexLens = Path.int().of("index")
        private val categoryFormLens = FormField.nonEmptyString().required("category")
        private val titleFormLens = FormField.nonEmptyString().required("title")
        private val cityFormLens = FormField.nonEmptyString().required("city")
        private val descriptionFormLens = FormField.nonEmptyString().required("description")
        private val announcementWithoutSpecialistFormLens = Body.webForm(
            Validator.Feedback,
            categoryFormLens,
            titleFormLens,
            cityFormLens,
            descriptionFormLens,
        ).toLens()
    }
    override fun invoke(request: Request): Response {
        val index = indexLens(request)
        val webForm = announcementWithoutSpecialistFormLens(request)
        return if (webForm.errors.isEmpty()) {
            addAnnouncementOperation.add(
                categoryFormLens(webForm),
                titleFormLens(webForm),
                cityFormLens(webForm),
                descriptionFormLens(webForm),
                index
            )
            Response(Status.FOUND).header("Location", "/specialists/$index")
        } else {
            val specialist = fetchSpecialistOperation.fetch(index) ?: return Response(Status.BAD_REQUEST)
            Response(Status.OK).with(
                htmlView of AnnouncementForSpecialistCreationFormVM(
                    listCategoriesOperation.list(),
                    listCitiesOperation.list(),
                    specialist.fullName,
                    webForm
                )
            )
        }
    }
}
