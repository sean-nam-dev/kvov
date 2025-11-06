package dev.sn.kvov.types

import dev.sn.kvov.core.ValidationBuilder
import dev.sn.kvov.result.Error

/**
 * Validates that the numeric value lies within the specified [bounds].
 *
 * @param bounds The inclusive range of valid values.
 * @return [Error.Number.RANGE] if the value is outside the range, or null if valid.
 */
fun <T> ValidationBuilder<T>.range(
    bounds: ClosedRange<T>
) where T: Number, T: Comparable<T> = add {
    if (it.toDouble() in bounds.start.toDouble()..bounds.endInclusive.toDouble()) null
    else Error.Number.RANGE
}

/**
 * Validates that the numeric value is strictly positive.
 *
 * @return [Error.Number.POSITIVE_ZERO] if the value is 0,
 * [Error.Number.POSITIVE] if it is negative, or null if positive.
 */
fun <T> ValidationBuilder<T>.positive() where T: Number, T: Comparable<T> = add {
    when {
        it.toDouble() == 0.0 -> Error.Number.POSITIVE_ZERO
        it.toDouble() < 0 -> Error.Number.POSITIVE
        else -> null
    }
}

/**
 * Validates that the numeric value is strictly negative.
 *
 * @return [Error.Number.NEGATIVE_ZERO] if the value is 0,
 * [Error.Number.NEGATIVE] if it is positive, or null if negative.
 */
fun <T> ValidationBuilder<T>.negative() where T: Number, T: Comparable<T> = add {
    when {
        it.toDouble() == 0.0 -> Error.Number.NEGATIVE_ZERO
        it.toDouble() > 0 -> Error.Number.NEGATIVE
        else -> null
    }
}

/**
 * Validates that the numeric value represents an even number.
 *
 * Supports fractional values by normalizing the decimal part.
 * @return [Error.Number.EVEN] if the value is odd, or null if even.
 */
fun <T> ValidationBuilder<T>.even() where T: Number, T: Comparable<T> = add {
    if (it.normalize() % 2 == 0L) null else Error.Number.EVEN
}

/**
 * Validates that the numeric value represents an odd number.
 *
 * Supports fractional values by normalizing the decimal part.
 * @return [Error.Number.ODD] if the value is even, or null if odd.
 */
fun <T> ValidationBuilder<T>.odd() where T: Number, T: Comparable<T> = add {
    if (it.normalize() % 2 == 0L) Error.Number.ODD else null
}

/**
 * Validates that the numeric value is equal to the specified [target].
 *
 * @param target The value to compare against.
 * @return [Error.Number.EQUAL] if not equal, or null if the values match.
 */
fun <T> ValidationBuilder<T>.equal(
    target: T
) where T: Number, T: Comparable<T> = add {
    if (it.toDouble() == target.toDouble()) null
    else Error.Number.EQUAL
}


/**
 * Converts a [Number] to a normalized [Long] by removing fractional digits.
 *
 * Used internally for parity checks (`even`/`odd`).
 * Prevents rounding errors for floating-point numbers.
 */
private fun Number.normalize(): Long {
    var num = this.toDouble()
    var count = 0
    while (num % 1.0 != 0.0 && count < 15) {
        num *= 10
        count++
    }
    return num.toLong()
}