package course.kotlin.client

import io.ktor.client.*
import io.ktor.client.features.websocket.*
import io.ktor.http.*
import io.ktor.http.cio.websocket.*
import kotlinx.coroutines.cancelAndJoin
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import java.net.http.WebSocket

suspend fun DefaultClientWebSocketSession.outputMessages() {
    try{
        for (message in incoming) {
            message as? Frame.Text ?: continue
            val text = message.readText()
            println(text)
        }
    } catch (e: Exception){
        println("Error receiving chat message: ${e.localizedMessage}")
        return
    }
}

suspend fun DefaultClientWebSocketSession.inputMessages() {
    while (true){
        val message = readLine() ?: ""
        if(message.equals("exit", true)) return
        try {
            send(message)
        } catch(e : Exception) {
            println("Error sending chat message: ${e.localizedMessage}")
            return
        }
    }
}

fun main() {
    val client = HttpClient {
        install(WebSockets)
    }
    runBlocking {
        client.webSocket(method = HttpMethod.Get, host = "127.0.0.1", port = 8080, path = "/chat") {
            val messageOutputHandler = launch{ outputMessages() }
            val messageInputHandler = launch{ inputMessages() }
            messageInputHandler.join() // wait for 'exit' or error
            messageInputHandler.cancelAndJoin()
        }
    }
    client.close()
    println("Connection closed. Goodbye!")
}
