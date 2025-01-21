package com.guilherme.wheretowatch.domain

typealias RootError = Error

sealed interface Error

sealed interface Result<out D, out E : RootError> {
    data class Success<out D : Any, out E : RootError>(val data: D) : Result<D, E>
    data class Error<out D, out E : RootError>(val error: E) : Result<D, E>
}

enum class ResponseError: Error {
    BAD_REQUEST,
    UNAUTHORIZED,
    FORBIDDEN,
    NOT_FOUND,
    METHOD_NOT_ALLOWED,
    REQUEST_TIMEOUT,
    TOO_MANY_REQUESTS,
    UNKNOWN
}