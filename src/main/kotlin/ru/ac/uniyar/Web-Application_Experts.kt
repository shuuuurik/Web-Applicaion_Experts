package ru.ac.uniyar

import org.flywaydb.core.api.FlywayException
import org.http4k.core.ContentType
import org.http4k.core.HttpHandler
import org.http4k.core.RequestContexts
import org.http4k.core.then
import org.http4k.filter.ServerFilters
import org.http4k.lens.RequestContextKey
import org.http4k.lens.RequestContextLens
import org.http4k.routing.ResourceLoader
import org.http4k.routing.routes
import org.http4k.routing.static
import org.http4k.server.Undertow
import org.http4k.server.asServer
import ru.ac.uniyar.config.readConfiguration
import ru.ac.uniyar.domain.database.entities.RolePermissions
import ru.ac.uniyar.domain.database.entities.User
import ru.ac.uniyar.domain.managers.H2DatabaseManager
import ru.ac.uniyar.domain.managers.connectToDatabase
import ru.ac.uniyar.domain.managers.performMigrations
import ru.ac.uniyar.domain.operations.OperationHolder
import ru.ac.uniyar.web.filters.JwtTools
import ru.ac.uniyar.web.filters.authenticationFilter
import ru.ac.uniyar.web.filters.authorizationFilter
import ru.ac.uniyar.web.filters.showErrorMessageFilter
import ru.ac.uniyar.web.handlers.HttpHandlerHolder
import ru.ac.uniyar.web.templates.ContextAwarePebbleTemplates
import ru.ac.uniyar.web.templates.ContextAwareViewRender

fun main() {
    val renderer = ContextAwarePebbleTemplates().hotReload("src/main/resources")
    val htmlView = ContextAwareViewRender.withContentType(renderer, ContentType.TEXT_HTML)
    val configuration = readConfiguration()

    val h2databaseManager = H2DatabaseManager(configuration.databaseConfig).initialize()
    try {
        performMigrations(configuration.databaseConfig)
    } catch (ex: FlywayException) {
        println("Вы накосячили с миграциями: ${ex.message}")
        h2databaseManager.stopServers()
        return
    }

    val database = connectToDatabase(configuration.databaseConfig)
    val operationHolder = OperationHolder(database, configuration.saltConfig.salt)

    val jwtTools = JwtTools(configuration.saltConfig.salt, "ru.ac.uniyar.LaboratoryWork3")

    val contexts = RequestContexts()
    val currentUserLens: RequestContextLens<User?> = RequestContextKey.optional(contexts, name = "user")
    val permissionsLens: RequestContextLens<RolePermissions> =
        RequestContextKey.required(contexts, name = "permissions")
    val htmlViewWithContext = htmlView
        .associateContextLens("currentUser", currentUserLens)
        .associateContextLens("permissions", permissionsLens)

    val handlerHolder =
        HttpHandlerHolder(currentUserLens, permissionsLens, htmlViewWithContext, operationHolder, jwtTools)
    val router = Router(handlerHolder)
    val staticFilesHandler = static(ResourceLoader.Classpath("/ru/ac/uniyar/public/"))

    val authorizedApp = authenticationFilter(
        currentUserLens,
        operationHolder.fetchUserViaUsernameOperation,
        jwtTools.subject,
    ).then(
        authorizationFilter(
            currentUserLens,
            permissionsLens,
            operationHolder.fetchRoleViaIdOperation
        )
    ).then(
        showErrorMessageFilter(
            htmlViewWithContext
        )
    ).then(
        router.invoke()
    )
    val app = routes(
        authorizedApp,
        staticFilesHandler
    )

    val printingApp: HttpHandler =
        ServerFilters.InitialiseRequestContext(contexts)
            .then(app)
    val webServer = printingApp.asServer(Undertow(port = configuration.webConfig.webPort)).start()

    println("Сервер доступен по адресу http://localhost:" + webServer.port())
    println("Веб-интерфейс базы данных доступен по адресу http://localhost:${configuration.databaseConfig.dbPort}")
    println("Введите любую строку, чтобы завершить работу приложения")
    readlnOrNull()
    webServer.stop()
    h2databaseManager.stopServers()
}
