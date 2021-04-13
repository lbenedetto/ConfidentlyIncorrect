package com.larsbenedetto.confidentlyincorrect.web.model

import org.springframework.http.ResponseEntity

class ErrorResponse(
    val message: String? = null,
    val stackTrace: String? = null,
)

class EmptyResponse

class ApiResponse<T>(
    var paging: PageInfoResponse? = null,
    var data: T? = null,
    var error: ErrorResponse? = null
) {
    companion object {
        fun <T> ok(data: T): ResponseEntity<ApiResponse<T>> {
            return ResponseEntity.ok(ApiResponse(data = data))
        }

        fun <T> ok(data: T, paging: PageInfoResponse?): ResponseEntity<ApiResponse<T>> {
            return ResponseEntity.ok(ApiResponse(data = data, paging = paging))
        }

        fun error(
            message: String?,
            exception: Exception?
        ): ApiResponse<Nothing> {
            return ApiResponse(error = ErrorResponse(message, exception?.stackTraceToString()))
        }

        fun error(
            exception: Exception
        ): ApiResponse<Nothing> {
            return error(exception.message, exception)
        }


        fun ok(): ResponseEntity<ApiResponse<EmptyResponse>> {
            return ok(EmptyResponse())
        }

        fun notFound(message: String?): ApiResponse<Nothing> {
            return error("Entity not found", null)
        }
    }
}
