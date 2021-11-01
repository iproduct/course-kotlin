package course.kotlin

import course.kotlin.dao.InMemoryRepository
import course.kotlin.dao.MyProductRepository
import course.kotlin.dao.Repository
import course.kotlin.fieldprop.FieldProperty
import course.kotlin.model.IdGenerator
import course.kotlin.model.Product
import course.kotlin.plugins.configureRouting
import course.kotlin.plugins.configureSerialization
import course.plugins.configureHTTP
import io.ktor.application.*
import io.ktor.request.*
import io.ktor.routing.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import io.ktor.util.pipeline.*
import java.util.concurrent.atomic.AtomicInteger
import kotlin.reflect.KProperty

private val sequence = AtomicInteger()
typealias ProductRepository = Repository<Int, Product>

private val ProductRepo: ProductRepository = InMemoryRepository(
    { sequence.incrementAndGet() },
//    object : IdGenerator<Int> {
//        private val sequence = AtomicInteger()
//        override fun nextId(): Int = sequence.incrementAndGet()
//    },
    listOf(
        Product("Laptop Lenovo", 1850.0),
        Product("Wireless Mouse", 25.7),
        Product("Wireless Keyboard", 35.2),
        Product("Whitboard Markers", 5.0),
    )
)

val Application.productRepo: ProductRepository by ::ProductRepo

val <S, C> PipelineContext<S, C>.productRepo: ProductRepository
        where S : Any, C : Any
        by FieldProperty({
            InMemoryRepository(
                { sequence.incrementAndGet() },
                listOf(Product("${this.context}", 1200.0)),
            )
        })

//class RouteDelegate {
//    operator fun getValue(thisRef: Route, property: KProperty<*>): ProductRepository {
//        return ProductRepository(
//            listOf(
//                ProductData("$thisRef, thank you for delegating '${property.name}", 1000.0),
//            )
//        )
//    }
//}


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
