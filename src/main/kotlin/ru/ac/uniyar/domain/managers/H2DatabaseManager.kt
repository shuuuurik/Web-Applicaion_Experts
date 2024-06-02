package ru.ac.uniyar.domain.managers

import org.h2.tools.Server
import ru.ac.uniyar.config.DataBaseConfig
import kotlin.concurrent.thread

class H2DatabaseManager(private val databaseConfig: DataBaseConfig) {
    private var tcpServer: Server? = null
    private var webServer: Server? = null
    private val shutdownThread = thread(start = false, name = "") {
        println("Stopping server")
        stopServers()
    }

    companion object {
        const val JDBC_CONNECTION = "jdbc:h2:tcp://localhost/database.h2"
        const val WEB_PORT = 8082
    }

    fun initialize(): H2DatabaseManager {
        startServers()
        registerShutdownHook()
        return this
    }

    private fun startServers() {
        tcpServer = Server.createTcpServer(
            "-tcpPort", "9092",
            "-baseDir", ".",
            "-ifNotExists",
        ).start()
        webServer = Server.createWebServer(
            "-webPort", databaseConfig.dbPort.toString(),
            "-baseDir", ".",
            "-ifNotExists",
        ).start()
    }

    private fun registerShutdownHook() {
        Runtime.getRuntime().addShutdownHook(shutdownThread)
    }

    fun stopServers() {
        tcpServer?.stop()
        tcpServer = null
        webServer?.stop()
        webServer = null
    }
}
