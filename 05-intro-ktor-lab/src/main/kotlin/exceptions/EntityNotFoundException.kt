package course.kotlin.exceptions

class EntityNotFoundException(override val message: String? = null, override val cause: Throwable? = null ):
    RuntimeException(message, cause) {}
