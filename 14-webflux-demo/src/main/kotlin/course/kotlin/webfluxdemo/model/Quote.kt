package course.kotlin.webfluxdemo.model

import java.time.Instant
import java.util.concurrent.atomic.AtomicLong

val idSequence = AtomicLong()
data class Quote(
    val symbol: String, val price : Double = 0.0,
    val instant: Instant = Instant.now(),
    val id: Long = idSequence.incrementAndGet(),
)
