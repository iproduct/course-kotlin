package course.kotlin.model

import java.time.LocalDateTime

data class Product(
    val id: Int,
    val name: String,
    val price: Double,
    val created:LocalDateTime = LocalDateTime.now(),
)
