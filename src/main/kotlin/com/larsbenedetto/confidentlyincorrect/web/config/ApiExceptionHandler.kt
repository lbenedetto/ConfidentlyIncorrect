package com.larsbenedetto.confidentlyincorrect.web.config

import com.larsbenedetto.confidentlyincorrect.web.model.ApiResponse
import com.larsbenedetto.confidentlyincorrect.web.model.EntityLookupFailedException
import com.larsbenedetto.confidentlyincorrect.web.model.ValidationException
import org.springframework.http.HttpStatus
import org.springframework.http.converter.HttpMessageNotReadableException
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler
import java.util.logging.Logger


@ControllerAdvice
@RestController
class ApiExceptionHandler(
    val logger: Logger
) : ResponseEntityExceptionHandler() {

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    fun handle(e: ValidationException): ApiResponse<Nothing> {
        logger.warn(e)
        return ApiResponse.error(e)
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.NOT_FOUND)
    fun handle(e: EntityLookupFailedException): ApiResponse<Nothing> {
        logger.warn(e)
        return ApiResponse.error(e)
    }

//    @ExceptionHandler
//    @ResponseStatus(HttpStatus.BAD_REQUEST)
//    fun handle(e: HttpMessageNotReadableException?) {
//        logger.warn("Returning HTTP 400 Bad Request", e)
//    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    fun handle(e: Exception): ApiResponse<Nothing> {
        logger.error(e)
        return ApiResponse.error(e)
    }
}

