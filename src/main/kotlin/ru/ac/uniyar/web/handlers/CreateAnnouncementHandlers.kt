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
import ru.ac.uniyar.domain.database.entities.User
import ru.ac.uniyar.domain.operations.AddAnnouncementOperation
import ru.ac.uniyar.domain.operations.FetchCategoryForSpecialistOperation
import ru.ac.uniyar.domain.operations.FetchDescriptionFromRequestOperation
import ru.ac.uniyar.domain.operations.ListCitiesOperation
import ru.ac.uniyar.web.lenses.lensOrNull
import ru.ac.uniyar.web.models.AnnouncementCreationFormVM
import ru.ac.uniyar.web.templates.ContextAwareViewRender

class ShowAnnouncementCreationFormHandler(
    private val currentUserLens: RequestContextLens<User?>,
    private val permissionsLens: RequestContextLens<RolePermissions>,
    private val htmlView: ContextAwareViewRender,
    private val listCitiesOperation: ListCitiesOperation,
    private val fetchCategoryForSpecialistOperation: FetchCategoryForSpecialistOperation,
    private val fetchDescriptionFromRequestOperation: FetchDescriptionFromRequestOperation
) : HttpHandler {
    companion object {
        private val indexLens = Path.int().of("index")
    }

    @Suppress("ReturnCount")
    override fun invoke(request: Request): Response {
        val permissions = permissionsLens(request)
        if (!permissions.addAnnouncement)
            return Response(Status.UNAUTHORIZED)
        val user = currentUserLens(request)!!
        val categoryId = lensOrNull(indexLens, request) ?: return Response(Status.BAD_REQUEST)
        val category = fetchCategoryForSpecialistOperation.fetch(user.username, categoryId)
            ?: return Response(Status.BAD_REQUEST)
        val education = fetchDescriptionFromRequestOperation.fetch(user.username, categoryId)!!
        return Response(Status.OK).with(
            htmlView(request) of AnnouncementCreationFormVM(
                category.name,
                listCitiesOperation.list(),
                user.fullName,
                education
            )
        )
    }
}

@Suppress("LongParameterList")
class AddAnnouncementHandler(
    private val currentUserLens: RequestContextLens<User?>,
    private val permissionsLens: RequestContextLens<RolePermissions>,
    private val htmlView: ContextAwareViewRender,
    private val listCitiesOperation: ListCitiesOperation,
    private val fetchCategoryForSpecialistOperation: FetchCategoryForSpecialistOperation,
    private val fetchDescriptionFromRequestOperation: FetchDescriptionFromRequestOperation,
    private val addAnnouncementOperation: AddAnnouncementOperation
) : HttpHandler {
    companion object {
        private val indexLens = Path.int().of("index")
        private val cityFormLens = FormField.nonEmptyString().required("city")
        private val titleFormLens = FormField.nonEmptyString().required("title")
        private val descriptionFormLens = FormField.nonEmptyString().required("description")
        private val announcementFormLens = Body.webForm(
            Validator.Feedback,
            titleFormLens,
            cityFormLens,
            descriptionFormLens,
        ).toLens()
    }

    @Suppress("ReturnCount")
    override fun invoke(request: Request): Response {
        val permissions = permissionsLens(request)
        if (!permissions.addAnnouncement)
            return Response(Status.UNAUTHORIZED)
        val user = currentUserLens(request)!!
        val categoryId = lensOrNull(indexLens, request) ?: return Response(Status.BAD_REQUEST)
        val category = fetchCategoryForSpecialistOperation.fetch(user.username, categoryId)
            ?: return Response(Status.BAD_REQUEST)
        val webForm = announcementFormLens(request)
        return if (webForm.errors.isEmpty()) {
            addAnnouncementOperation.add(
                categoryId,
                titleFormLens(webForm),
                cityFormLens(webForm),
                descriptionFormLens(webForm),
                user.username
            )
            Response(Status.FOUND).header("Location", "/announcements")
        } else {
            val education = fetchDescriptionFromRequestOperation.fetch(user.username, categoryId)!!
            Response(Status.OK).with(
                htmlView(request) of AnnouncementCreationFormVM(
                    category.name,
                    listCitiesOperation.list(),
                    user.fullName,
                    education,
                    webForm
                )
            )
        }
    }
}
