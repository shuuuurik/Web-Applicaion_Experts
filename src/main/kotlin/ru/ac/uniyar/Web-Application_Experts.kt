package ru.ac.uniyar

import org.flywaydb.core.api.FlywayException
import org.http4k.core.Body
import org.http4k.core.ContentType
import org.http4k.core.HttpHandler
import org.http4k.core.then
import org.http4k.routing.ResourceLoader
import org.http4k.routing.routes
import org.http4k.routing.static
import org.http4k.server.Undertow
import org.http4k.server.asServer
import org.http4k.template.PebbleTemplates
import org.http4k.template.viewModel
import ru.ac.uniyar.domain.managers.H2DatabaseManager
import ru.ac.uniyar.domain.managers.connectToDatabase
import ru.ac.uniyar.domain.managers.performMigrations
import ru.ac.uniyar.domain.operations.OperationHolder
import ru.ac.uniyar.web.filters.showErrorMessageFilter
import ru.ac.uniyar.web.handlers.HttpHandlerHolder

/*fun app(
    htmlView: BiDiBodyLens<ViewModel>,
    operationHolder: OperationHolder,
): HttpHandler = routes(
    "/home" bind GET to ShowHomePageHandler(htmlView, operationHolder),
    "/home/erm" bind GET to ShowERMHandler(htmlView),

    "/announcements/new" bind announcementCreationRoute(htmlView, operationHolder),
    "/announcements" bind GET to ShowAnnouncementsListHandler(
        htmlView,
        operationHolder.listAnnouncementsByCategoryTitleAndCityOperation,
        operationHolder.listCategoriesOperation,
        operationHolder.listCitiesOperation
    ),
    "/announcements/{index}" bind GET to ShowAnnouncementHandler(
        htmlView,
        operationHolder.fetchAnnouncementOperation,
        operationHolder.fetchSpecialistOperation
    ),

    "/cities/new" bind cityCreationRoute(htmlView, operationHolder),
    "/cities" bind GET to ShowCitiesListHandler(
        htmlView,
        operationHolder.listCitiesByNameOperation
    ),
    "/cities/{name}" bind GET to ShowCityHandler(
        htmlView,
        operationHolder.fetchCityOperation,
        operationHolder.listCategoriesByCityOperation
    ),

    "/categories/new" bind categoryCreationRoute(htmlView, operationHolder),
    "/categories" bind GET to ShowCategoriesListHandler(
        htmlView,
        operationHolder.listCategoriesByNameOperation
    ),
    "/categories/{name}" bind GET to ShowCategoryHandler(htmlView, operationHolder.fetchCategoryOperation),

    "/specialists/new" bind specialistCreationRoute(htmlView, operationHolder.addSpecialistOperation),
    "/specialists" bind GET to ShowSpecialistsListHandler(
        htmlView,
        operationHolder.listSpecialistsByNameAndPhoneOperation
    ),
    "/specialists/{index}/new" bind announcementForSpecialistCreationRoute(htmlView, operationHolder),
    "/specialists/{index}" bind GET to ShowSpecialistHandler(
        htmlView,
        operationHolder.fetchSpecialistOperation,
        operationHolder.listAnnouncementsBySpecialistOperation
    ),

    "/" bind GET to redirectToHome(),
    static(ResourceLoader.Classpath("/ru/ac/uniyar/public/")),
)*/

fun main() {
    val renderer = PebbleTemplates().HotReload("src/main/resources")
    val htmlView = Body.viewModel(renderer, ContentType.TEXT_HTML).toLens()

    val h2databaseManager = H2DatabaseManager().initialize()
    try {
        performMigrations()
    } catch (ex: FlywayException) {
        println("Вы накосячили с миграциями: ${ex.message}")
        h2databaseManager.stopServers()
        return
    }

    val database = connectToDatabase()
    val operationHolder = OperationHolder(database)

    val handlerHolder = HttpHandlerHolder(htmlView, operationHolder)
    val router = Router(handlerHolder)
    val staticFilesHandler = static(ResourceLoader.Classpath("/ru/ac/uniyar/public/"))
    val app = routes(
        router.invoke(),
        staticFilesHandler
    )

    val printingApp: HttpHandler =
        showErrorMessageFilter(htmlView)
            // .then(DebuggingFilters.PrintRequestAndResponse())
            .then(app)
    val webServer = printingApp.asServer(Undertow(port = 9000)).start()

    println("Сервер доступен по адресу http://localhost:" + webServer.port())
    println("Веб-интерфейс базы данных доступен по адресу http://localhost:${H2DatabaseManager.WEB_PORT}")
    println("Введите любую строку, чтобы завершить работу приложения")
    readlnOrNull()
    webServer.stop()
    h2databaseManager.stopServers()
}
