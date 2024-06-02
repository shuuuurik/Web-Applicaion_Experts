package ru.ac.uniyar.web.handlers

import org.http4k.core.Body
import org.http4k.core.HttpHandler
import org.http4k.core.Request
import org.http4k.core.Response
import org.http4k.core.Status
import org.http4k.core.cookie.Cookie
import org.http4k.core.cookie.SameSite
import org.http4k.core.cookie.cookie
import org.http4k.core.cookie.invalidateCookie
import org.http4k.core.with
import org.http4k.lens.FormField
import org.http4k.lens.Invalid
import org.http4k.lens.RequestContextLens
import org.http4k.lens.Validator
import org.http4k.lens.nonEmptyString
import org.http4k.lens.webForm
import ru.ac.uniyar.domain.database.entities.User
import ru.ac.uniyar.domain.operations.AuthenticateUserViaLoginOperation
import ru.ac.uniyar.domain.operations.AuthenticationError
import ru.ac.uniyar.web.filters.JwtTools
import ru.ac.uniyar.web.models.LoginFormVM
import ru.ac.uniyar.web.templates.ContextAwareViewRender

class ShowLoginFormHandler(
    private val currentUserLens: RequestContextLens<User?>,
    private val htmlView: ContextAwareViewRender
) : HttpHandler {
    override fun invoke(request: Request): Response {
        if (currentUserLens(request) != null) {
            return Response(Status.BAD_REQUEST)
        }
        return Response(Status.OK).with(htmlView(request) of LoginFormVM())
    }
}

class AuthenticateUser(
    private val currentUserLens: RequestContextLens<User?>,
    private val htmlView: ContextAwareViewRender,
    private val authenticateUserViaLoginOperation: AuthenticateUserViaLoginOperation,
    private val jwtTools: JwtTools,
) : HttpHandler {
    companion object {
        private val usernameFieldLens = FormField.nonEmptyString().required("username")
        private val passwordFieldLens = FormField.nonEmptyString().required("password")
        private val formLens = Body.webForm(Validator.Feedback, usernameFieldLens, passwordFieldLens).toLens()
    }

    @Suppress("ReturnCount")
    override fun invoke(request: Request): Response {
        if (currentUserLens(request) != null) {
            return Response(Status.BAD_REQUEST)
        }
        val form = formLens(request)
        if (form.errors.isNotEmpty()) {
            return Response(Status.OK).with(htmlView(request) of LoginFormVM(form))
        }
        val username = try {
            authenticateUserViaLoginOperation.authenticate(
                usernameFieldLens(form),
                passwordFieldLens(form)
            )
        } catch (_: AuthenticationError) {
            val newErrors = form.errors + Invalid(
                passwordFieldLens.meta.copy(description = "Имя пользователя или пароль были введены неверно")
            )
            val newForm = form.copy(errors = newErrors)
            return Response(Status.OK).with(htmlView(request) of LoginFormVM(newForm))
        }
        val token = jwtTools.create(username) ?: return Response(Status.INTERNAL_SERVER_ERROR)
        val authCookie = Cookie("auth_token", token, httpOnly = true, sameSite = SameSite.Strict)
        return Response(Status.FOUND)
            .header("Location", "/")
            .cookie(authCookie)
    }
}

class LogOutUser : HttpHandler {
    override fun invoke(request: Request): Response {
        return Response(Status.FOUND).header("Location", "/").invalidateCookie("auth_token")
    }
}
