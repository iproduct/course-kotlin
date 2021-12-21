package course.kotlin.server.plugins

import io.ktor.http.cio.websocket.*
import io.ktor.websocket.*
import java.time.*
import io.ktor.application.*
import io.ktor.network.sockets.*
import io.ktor.response.*
import io.ktor.request.*
import io.ktor.routing.*
import java.util.*
import kotlin.collections.LinkedHashSet
import course.kotlin.server.model.Connection

fun Application.configureSockets() {
    install(WebSockets) {
        pingPeriod = Duration.ofSeconds(15)
        timeout = Duration.ofSeconds(15)
        maxFrameSize = Long.MAX_VALUE
        masking = false
    }

    routing {
        val connections = Collections.synchronizedSet<Connection>(LinkedHashSet())
        webSocket("/chat") { // websocketSession
            val thisConnection = Connection(this)
            log.info("New user connected: ${thisConnection.name}")
            connections += thisConnection
            try {
                send("You are connected as: '${thisConnection.name}'! There are ${connections.count()} users in chat.")

                for (frame in incoming) {
                    when (frame) {
                        is Frame.Text -> {
                            val text = frame.readText()
                            val textWithUsername = "${thisConnection.name} said: $text"
                            connections.forEach({
                                it.session.send(Frame.Text(textWithUsername))
                            })
                        }
                        else -> continue
                    }
                }
            } catch (e: Exception) {
                log.error("Error receiving chat message", e)
            } finally {
                log.info("Closing connection for user: ${thisConnection.name}")
                connections -= thisConnection
                try {
                    val userLeftCaht = "User ${thisConnection.name} left the chat."
                    connections.forEach({
                        it.session.send(Frame.Text(userLeftCaht))
                    })
                } catch (e: Exception) {
                    log.error("Error receiving chat message", e)
                }
            }
        }
    }
}
