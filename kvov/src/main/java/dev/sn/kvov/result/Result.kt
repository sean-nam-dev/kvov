package dev.sn.kvov.result

sealed class Result {
    data object Success: Result()
    data class Failure(val errors: List<Error>): Result()
}