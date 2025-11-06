package dev.sn.kvov.core

import dev.sn.kvov.dsl.KvovDsl
import dev.sn.kvov.result.Result
import dev.sn.kvov.result.Error

@KvovDsl
open class ValidationBuilder<T>(private val value: T) {
    private val rules = mutableListOf<(T) -> Error?>()

    fun add(rule: (T) -> Error?) {
        rules += rule
    }

    internal fun build(): Result {
        val errors = rules.mapNotNull { it(value) }

        return if (errors.isEmpty()) Result.Success
                else Result.Failure(errors)
    }
}

fun <T> validate(
    value: T,
    block: ValidationBuilder<T>.() -> Unit
): Result = ValidationBuilder(value).apply(block).build()