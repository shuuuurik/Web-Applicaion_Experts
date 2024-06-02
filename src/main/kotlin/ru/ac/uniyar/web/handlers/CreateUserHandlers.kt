package ru.ac.uniyar.web.handlers

import org.http4k.core.Body
import org.http4k.core.HttpHandler
import org.http4k.core.Request
import org.http4k.core.Response
import org.http4k.core.Status
import org.http4k.core.cookie.Cookie
import org.http4k.core.cookie.SameSite
import org.http4k.core.cookie.cookie
import org.http4k.core.with
import org.http4k.lens.FormField
import org.http4k.lens.Invalid
import org.http4k.lens.RequestContextLens
import org.http4k.lens.Validator
import org.http4k.lens.nonEmptyString
import org.http4k.lens.webForm
import ru.ac.uniyar.domain.database.entities.User
import ru.ac.uniyar.domain.operations.AddUserOperation
import ru.ac.uniyar.domain.operations.ListCitiesOperation
import ru.ac.uniyar.web.filters.JwtTools
import ru.ac.uniyar.web.lenses.lensOrNull
import ru.ac.uniyar.web.models.UserCreationFormVM
import ru.ac.uniyar.web.templates.ContextAwareViewRender
import java.sql.SQLIntegrityConstraintViolationException

class ShowUserCreationFormHandler(
    private val currentUserLens: RequestContextLens<User?>,
    private val htmlView: ContextAwareViewRender,
    private val listCitiesOperation: ListCitiesOperation,
) : HttpHandler {
    override fun invoke(request: Request): Response {
        if (currentUserLens(request) != null) {
            return Response(Status.BAD_REQUEST)
        }
        return Response(Status.OK).with(
            htmlView(request) of UserCreationFormVM(
                listCitiesOperation.list()
            )
        )
    }
}

class AddUserHandler(
    private val currentUserLens: RequestContextLens<User?>,
    private val htmlView: ContextAwareViewRender,
    private val listCitiesOperation: ListCitiesOperation,
    private val addUserOperation: AddUserOperation,
    private val jwtTools: JwtTools
) : HttpHandler {
    companion object {
        private val fullNameFormLens = FormField.nonEmptyString().required("full_name")
        private val phoneFormLens = FormField.nonEmptyString().required("phone")
        private val cityFormLens = FormField.nonEmptyString().required("city")
        private val usernameFormLens = FormField.nonEmptyString().required("username")
        private val passwordOneFormLens = FormField.nonEmptyString().required("passwordOne")
        private val passwordTwoFormLens = FormField.nonEmptyString().required("passwordTwo")
        private val userFormLens = Body.webForm(
            Validator.Feedback,
            fullNameFormLens,
            phoneFormLens,
            usernameFormLens,
            passwordOneFormLens,
            passwordTwoFormLens,
            cityFormLens,
        ).toLens()
    }
    @Suppress("ReturnCount")
    override fun invoke(request: Request): Response {
        if (currentUserLens(request) != null) {
            return Response(Status.BAD_REQUEST)
        }
        val cities = listCitiesOperation.list()
        var webForm = userFormLens(request)
        val firstPassword = lensOrNull(passwordOneFormLens, webForm)
        val secondPassword = lensOrNull(passwordTwoFormLens, webForm)
        if (firstPassword != null && secondPassword != null && firstPassword != secondPassword) {
            val newErrors = webForm.errors +
                Invalid(passwordOneFormLens.meta.copy(description = "Введённые пароли не совпадают"))
            webForm = webForm.copy(errors = newErrors)
        }
        if (webForm.errors.isEmpty()) {
            try {
                val username = usernameFormLens(webForm)
                addUserOperation.add(
                    username,
                    passwordOneFormLens(webForm),
                    fullNameFormLens(webForm),
                    phoneFormLens(webForm),
                    cityFormLens(webForm),
                )
                val token = jwtTools.create(username) ?: return Response(Status.INTERNAL_SERVER_ERROR)
                val authCookie = Cookie("auth_token", token, httpOnly = true, sameSite = SameSite.Strict)
                return Response(Status.FOUND)
                    .header("Location", "/")
                    .cookie(authCookie)
            } catch (_: SQLIntegrityConstraintViolationException) {
                val newErrors = webForm.errors +
                    Invalid(passwordOneFormLens.meta.copy(description = "Пользователь с таким логином уже существует"))
                webForm = webForm.copy(errors = newErrors)
                return Response(Status.OK).with(
                    htmlView(request) of UserCreationFormVM(cities, webForm)
                )
            }
        } else {
            return Response(Status.OK).with(htmlView(request) of UserCreationFormVM(cities, webForm))
        }
    }
}
