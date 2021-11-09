package course.kotlin.variance.course.kotlin.exception

class InvalidClientDataException(override val message: String? = null, override val cause: Throwable? = null):
    Exception(message, cause) {}
