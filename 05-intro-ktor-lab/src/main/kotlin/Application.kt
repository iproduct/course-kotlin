package course.kotlin

import course.kotlin.dao.ProductRepository
import course.kotlin.model.ProductData
import course.kotlin.plugins.configureRouting
import course.kotlin.plugins.configureSerialization
import course.plugins.configureHTTP
import io.ktor.application.*
import io.ktor.routing.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import kotlin.reflect.KProperty

private val ProductRepo = ProductRepository(
    listOf(
        ProductData("Laptop Lenovo", 1850.0),
        ProductData("Wireless Mouse", 25.7),
        ProductData("Wireless Keyboard", 35.2),
        ProductData("Whitboard Markers", 5.0),
    )
)

//val Application.productRepo: ProductRepository by ::ProductRepo
val Route.productRepo by RouteDelegate()

class RouteDelegate {
    operator fun getValue(thisRef: Route, property: KProperty<*>): ProductRepository {
        return ProductRepository(
            listOf(
                ProductData("$thisRef, thank you for delegating '${property.name}", 1000.0),
            )
        )
    }
}


    fun main() {
        embeddedServer(
            Netty,
            port = 8080,
            host = "localhost",
            watchPaths = listOf("classes", "resources"),
        ) {
            configureRouting()
            configureHTTP()
            configureSerialization()
        }.start(wait = true)
    }
