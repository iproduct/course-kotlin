package ktor.example.auth.jwt

import io.ktor.application.Application
import io.ktor.server.cio.CIO
import io.ktor.server.engine.embeddedServer

fun startServer() = embeddedServer(CIO, watchPaths = listOf("build/classes"), port=5000,
    module= Application::module)
        .start(true)

