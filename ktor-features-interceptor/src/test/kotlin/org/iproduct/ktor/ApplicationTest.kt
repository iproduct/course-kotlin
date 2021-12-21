package org.iproduct.ktor

import io.ktor.application.*
import io.ktor.response.*
import io.ktor.request.*
import io.ktor.routing.*
import io.ktor.html.*
import kotlinx.html.*
import io.ktor.content.*
import io.ktor.http.content.*
import io.ktor.features.*
import io.ktor.gson.*
import io.ktor.client.*
import io.ktor.http.*
import io.ktor.server.testing.*
import org.junit.Assert.assertEquals
import org.junit.Test

class ApplicationTest {
    @Test
    fun testRoot() {
        withTestApplication({ mymodule() }) {
            handleRequest(HttpMethod.Get, "/").apply {
                assertEquals(HttpStatusCode.OK, response.status())
                assertEquals("<!DOCTYPE html>\n" +
                        "<html>\n" +
                        "  <body>\n" +
                        "    <h2><a href=\"/products\">Go to /products</a></h2>\n" +
                        "  </body>\n" +
                        "</html>\n", response.content)
            }
        }
    }
}
