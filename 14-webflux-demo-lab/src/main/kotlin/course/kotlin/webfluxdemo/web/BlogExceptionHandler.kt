package course.kotlin.webfluxdemo.web

import course.kotlin.spring.exception.EntityNotFoundException
import course.kotlin.spring.exception.InvalidEntityDataException
import course.kotlin.spring.exception.UnauthorisedException
import course.kotlin.webfluxdemo.model.ErrorResponse
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.reactive.function.server.ServerResponse
import reactor.core.publisher.Mono

@ControllerAdvice
class BlogExceptionHandler {
    @ExceptionHandler
    fun handleEntityNotfoundException(ex: EntityNotFoundException) =
        ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                ErrorResponse(HttpStatus.NOT_FOUND.value(), ex.message, ex)
            )

    @ExceptionHandler
    fun handleInvalidEntityDataException(ex: InvalidEntityDataException) =
        ResponseEntity.badRequest().body(
            ErrorResponse(HttpStatus.BAD_REQUEST.value(), ex.message, ex)
        )

    @ExceptionHandler
    fun handleEntityNotfoundException(ex: UnauthorisedException) =
        ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(
            ErrorResponse(HttpStatus.UNAUTHORIZED.value(), ex.message, ex)
        )

}
