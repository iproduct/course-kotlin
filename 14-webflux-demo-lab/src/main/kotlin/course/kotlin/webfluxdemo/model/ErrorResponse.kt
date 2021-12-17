package course.kotlin.webfluxdemo.model

import java.lang.Exception

data class ErrorResponse(
    val status: Int,
    val message: String? = null,
    val exception: Exception? = null,
    val violations: List<String>? = null,
) {
}
