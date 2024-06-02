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
import ru.ac.uniyar.domain.operations.EditAnnouncementOperation
import ru.ac.uniyar.domain.operations.FetchAnnouncementOperation
import ru.ac.uniyar.domain.operations.ListCategoriesForSpecialistOperation
import ru.ac.uniyar.domain.operations.ListCitiesOperation
import ru.ac.uniyar.web.lenses.lensOrNull
import ru.ac.uniyar.web.models.AnnouncementEditingFormVM
import ru.ac.uniyar.web.templates.ContextAwareViewRender

class ShowAnnouncementEditingFormHandler(
    private val currentUserLens: RequestContextLens<User?>,
    private val permissionsLens: RequestContextLens<RolePermissions>,
    private val htmlView: ContextAwareViewRender,
    private val fetchAnnouncementOperation: FetchAnnouncementOperation,
    private val listCategoriesForSpecialistOperation: ListCategoriesForSpecialistOperation,
    private val listCitiesOperation: ListCitiesOperation,
) : HttpHandler {
    companion object {
        private val indexLens = Path.int().of("index")
    }

    @Suppress("ReturnCount")
    override fun invoke(request: Request): Response {
        val permissions = permissionsLens(request)
        if (!permissions.editAnnouncement)
            return Response(Status.UNAUTHORIZED)
        val user = currentUserLens(request)!!
        val announcementId = lensOrNull(indexLens, request) ?: return Response(Status.BAD_REQUEST)
        val announcement = fetchAnnouncementOperation.fetch(announcementId)
            ?: return Response(Status.BAD_REQUEST)
        return Response(Status.OK).with(
            htmlView(request) of AnnouncementEditingFormVM(
                announcement,
                listCategoriesForSpecialistOperation.list(user.username),
                listCitiesOperation.list()
            )
        )
    }
}

@Suppress("LongParameterList")
class EditAnnouncementHandler(
    private val currentUserLens: RequestContextLens<User?>,
    private val permissionsLens: RequestContextLens<RolePermissions>,
    private val htmlView: ContextAwareViewRender,
    private val fetchAnnouncementOperation: FetchAnnouncementOperation,
    private val listCategoriesForSpecialistOperation: ListCategoriesForSpecialistOperation,
    private val listCitiesOperation: ListCitiesOperation,
    private val editAnnouncementOperation: EditAnnouncementOperation
) : HttpHandler {
    companion object {
        private val indexLens = Path.int().of("index")
        private val categoryFormLens = FormField.int().required("category")
        private val cityFormLens = FormField.nonEmptyString().required("city")
        private val titleFormLens = FormField.nonEmptyString().required("title")
        private val descriptionFormLens = FormField.nonEmptyString().required("description")
        private val announcementFormLens = Body.webForm(
            Validator.Feedback,
            categoryFormLens,
            cityFormLens,
            titleFormLens,
            descriptionFormLens,
        ).toLens()
    }

    @Suppress("ReturnCount")
    override fun invoke(request: Request): Response {
        val permissions = permissionsLens(request)
        if (!permissions.editAnnouncement)
            return Response(Status.UNAUTHORIZED)
        val webForm = announcementFormLens(request)
        val user = currentUserLens(request)!!
        val announcementId = lensOrNull(indexLens, request) ?: return Response(Status.BAD_REQUEST)
        val announcement = fetchAnnouncementOperation.fetch(announcementId)
            ?: return Response(Status.BAD_REQUEST)
        return if (webForm.errors.isEmpty()) {
            editAnnouncementOperation.edit(
                announcementId, categoryFormLens(webForm), cityFormLens(webForm),
                titleFormLens(webForm), descriptionFormLens(webForm)
            )
            Response(Status.FOUND).header("Location", "/announcements/$announcementId")
        } else {
            Response(Status.OK)
                .with(
                    htmlView(request) of AnnouncementEditingFormVM(
                        announcement,
                        listCategoriesForSpecialistOperation.list(user.username),
                        listCitiesOperation.list(),
                        webForm
                    )
                )
        }
    }
}
