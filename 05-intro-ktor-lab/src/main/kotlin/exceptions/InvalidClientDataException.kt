package course.kotlin.exceptions

class InvalidClientDataException(override val message: String? = null, override val cause: Throwable? = null ):
    Throwable(message, cause) {}
