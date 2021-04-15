package com.larsbenedetto.confidentlyincorrect.web.model

import com.larsbenedetto.confidentlyincorrect.domain.Identity
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler

class ValidationException(override val message: String?) : RuntimeException(message)

class EntityLookupFailedException(
    entityName: String?,
    entityId: Identity<*>
) : RuntimeException("Could not find entity $entityName for id ${entityId.value}")

@ControllerAdvice
@RestController
class ApiExceptionHandler : ResponseEntityExceptionHandler() {
    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    private fun handle(e: ValidationException): ApiResponse<Nothing> {
        return ApiResponse.error(e)
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.NOT_FOUND)
    private fun handle(e: EntityLookupFailedException): ApiResponse<Nothing> {
        return ApiResponse.notFound(e.message)
    }
}