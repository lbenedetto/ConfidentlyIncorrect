package com.larsbenedetto.confidentlyincorrect.web.config

import com.larsbenedetto.confidentlyincorrect.web.model.ApiResponse
import com.larsbenedetto.confidentlyincorrect.web.model.EntityLookupFailedException
import com.larsbenedetto.confidentlyincorrect.web.model.ValidationException
import io.github.oshai.kotlinlogging.KLogger
import io.github.oshai.kotlinlogging.KotlinLogging
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler


@ControllerAdvice
@RestController
class ApiExceptionHandler : ResponseEntityExceptionHandler() {
    private val log: KLogger = KotlinLogging.logger {}

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    fun handle(e: ValidationException): ApiResponse<Nothing> {
        log.warn(e) { e.message }
        return ApiResponse.error(e)
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.NOT_FOUND)
    fun handle(e: EntityLookupFailedException): ApiResponse<Nothing> {
        log.warn(e) { e.message }
        return ApiResponse.error(e)
    }

//    @ExceptionHandler
//    @ResponseStatus(HttpStatus.BAD_REQUEST)
//    fun handle(e: HttpMessageNotReadableException?) {
//        log.warn(e) { "Returning HTTP 400 Bad Request" }
//    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    fun handle(e: Exception): ApiResponse<Nothing> {
        log.error(e) { e.message }
        return ApiResponse.error(e)
    }
}
