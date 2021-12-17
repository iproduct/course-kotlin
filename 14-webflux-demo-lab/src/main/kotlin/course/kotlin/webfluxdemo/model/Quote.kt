package course.kotlin.webfluxdemo.model

import java.time.Instant
import java.util.concurrent.atomic.AtomicLong

data class Quote(
    val symbol: String,
    val price : Double = 0.0,
    val instant: Instant = Instant.now(),
)
