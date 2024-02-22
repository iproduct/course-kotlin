package com.jetbrains.handson.httpapi

import com.jetbrains.handson.httpapi.routes.registerCustomerRoutes
import com.jetbrains.handson.httpapi.routes.registerOrderRoutes
import com.sun.xml.internal.ws.client.ContentNegotiation
import io.ktor.server.application.*
import io.ktor.server.plugins.*

fun main(args: Array<String>): Unit = io.ktor.server.netty.EngineMain.main(args)

fun Application.module(testing: Boolean = false) {
    install(ContentNegotiation) {
        json()
    }
    registerCustomerRoutes()
    registerOrderRoutes()
}
