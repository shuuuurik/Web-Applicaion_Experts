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
import org.http4k.lens.webForm
import ru.ac.uniyar.domain.database.entities.RolePermissions
import ru.ac.uniyar.domain.database.entities.User
import ru.ac.uniyar.domain.operations.ListCategoriesForSpecialistOperation
import ru.ac.uniyar.web.models.CategorySelectionFormVM
import ru.ac.uniyar.web.templates.ContextAwareViewRender

class ShowCategoriesForAnnouncementFormHandler(
    private val currentUserLens: RequestContextLens<User?>,
    private val permissionsLens: RequestContextLens<RolePermissions>,
    private val htmlView: ContextAwareViewRender,
    private val listCategoriesForSpecialistOperation: ListCategoriesForSpecialistOperation,
) : HttpHandler {
    override fun invoke(request: Request): Response {
        val permissions = permissionsLens(request)
        if (!permissions.addAnnouncement)
            return Response(Status.UNAUTHORIZED)
        val currentUser = currentUserLens(request)!!
        val categories = listCategoriesForSpecialistOperation.list(currentUser.username)
        return Response(Status.OK).with(
            htmlView(request) of CategorySelectionFormVM(categories)
        )
    }
}

class SelectCategoryHandler(
    private val currentUserLens: RequestContextLens<User?>,
    private val permissionsLens: RequestContextLens<RolePermissions>,
    private val htmlView: ContextAwareViewRender,
    private val listCategoriesForSpecialistOperation: ListCategoriesForSpecialistOperation
) : HttpHandler {
    companion object {
        private val categoryFormLens = FormField.int().required("category")
        private val categoryWebFormLens = Body.webForm(
            Validator.Feedback,
            categoryFormLens,
        ).toLens()
    }
    override fun invoke(request: Request): Response {
        val permissions = permissionsLens(request)
        if (!permissions.addAnnouncement)
            return Response(Status.UNAUTHORIZED)
        val currentUser = currentUserLens(request)!!
        val webForm = categoryWebFormLens(request)
        return if (webForm.errors.isEmpty()) {
            val categoryId = categoryFormLens(webForm)
            Response(Status.FOUND).header("Location", "/announcements/new/$categoryId")
        } else {
            Response(Status.OK).with(
                htmlView(request) of CategorySelectionFormVM(
                    listCategoriesForSpecialistOperation.list(currentUser.username),
                    webForm
                )
            )
        }
    }
}
