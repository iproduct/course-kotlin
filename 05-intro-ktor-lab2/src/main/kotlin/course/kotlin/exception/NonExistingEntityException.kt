package course.kotlin.exception

class NonExistingEntityException(override val message: String? = null, override val cause: Throwable? = null):
    Exception(message, cause) {}
