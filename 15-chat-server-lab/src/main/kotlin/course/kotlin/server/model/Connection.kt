package course.kotlin.server.model

import io.ktor.http.cio.websocket.*
import java.util.concurrent.atomic.AtomicInteger

class Connection(val session: DefaultWebSocketSession) {
    companion object {
        val lastId = AtomicInteger()
    }
    val name = "user${lastId.getAndIncrement()}"
}
