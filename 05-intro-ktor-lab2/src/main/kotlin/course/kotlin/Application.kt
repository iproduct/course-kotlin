package course.kotlin


import course.kotlin.dao.*
import course.kotlin.fielddelegate.FieldPropertyDelegate
import course.kotlin.model.Product
import course.kotlin.plugins.configureHTTP
import course.kotlin.plugins.configureMonitoring
import course.kotlin.plugins.configureRouting
import course.kotlin.plugins.configureSerialization
import course.kotlin.variance.course.kotlin.dao.IdGenerator
import course.kotlin.variance.course.kotlin.dao.Repository
import io.ktor.application.*
import io.ktor.request.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import io.ktor.util.pipeline.*
import java.util.concurrent.atomic.AtomicInteger
import javax.security.auth.Subject

typealias ProductRepository = Repository<Int, Product>

private val productsSequence = AtomicInteger()
private val usersSequence = AtomicInteger()

private val productRepository: ProductRepository = InMemoryRepository(
    { productsSequence.incrementAndGet() },
//    object: IdGenerator<Int> {
//        private val idSequence = AtomicInteger()
//        override fun getNextId(): Int = idSequence.incrementAndGet()
//    },
    listOf(
        Product("Laptop Computer", 1500.0),
        Product("Computer Mouse", 25.7),
        Product("Wireless Keyboard", 35.2),
        Product("Whiteboard Markers", 4.7),
    ),
)

val <Subject:Any, Call:Any> PipelineContext<Subject, Call>.productRepo: ProductRepository
by FieldPropertyDelegate({
    InMemoryRepository(
        object: IdGenerator<Int> {
            private val idSequence = AtomicInteger()
            override fun getNextId(): Int = idSequence.incrementAndGet()
        },
        listOf(
            Product("${this.context}", 1500.0),
        ),
    )
})

fun main() {
    embeddedServer(Netty, port = 8080, host = "localhost", watchPaths = listOf("classes", "resource")) {
        configureRouting()
        configureHTTP()
        configureMonitoring()
        configureSerialization()
    }.start(wait = true)
}
