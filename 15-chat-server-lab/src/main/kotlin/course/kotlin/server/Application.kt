package course.kotlin.server

import course.kotlin.server.plugins.configureHTTP
import course.kotlin.server.plugins.configureMonitoring
import course.kotlin.server.plugins.configureRouting
import course.kotlin.server.plugins.configureSockets
import io.ktor.application.*

fun main(args: Array<String>): Unit =
    io.ktor.server.netty.EngineMain.main(args)

@Suppress("unused") // application.conf references the main function. This annotation prevents the IDE from marking it as unused.
fun Application.module() {
//    configureRouting()
//    configureHTTP()
    configureMonitoring()
    configureSockets()
}
