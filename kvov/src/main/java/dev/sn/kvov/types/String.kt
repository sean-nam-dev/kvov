package dev.sn.kvov.types

import dev.sn.kvov.core.ValidationBuilder
import dev.sn.kvov.result.Error

/**
 * Validates that the string contains the specified substring [sub].
 *
 * @param sub The substring that must be present.
 * @return [Error.String.SUBSTRING] if the substring is missing, or null if found.
 */
fun ValidationBuilder<String>.substring(
    sub: String
) = add {
    if (!it.contains(sub)) Error.String.SUBSTRING else null
}

/**
 * Validates whether the string may contain whitespace characters.
 *
 * @param allow If false, validation fails when any whitespace is present.
 * @return [Error.String.WHITESPACE] if whitespace is not allowed but found, or null otherwise.
 */
fun ValidationBuilder<String>.whitespace(
    allow: Boolean
) = add {
    when {
        !allow && Regex("\\s").containsMatchIn(it) ->
            Error.String.WHITESPACE
        else -> null
    }
}

/**
 * Validates that the string length lies within the specified bounds.
 *
 * @param min The minimum allowed length.
 * @param max The maximum allowed length. Defaults to [Int.MAX_VALUE].
 * @return [Error.String.MIN_LENGTH] if too short, [Error.String.MAX_LENGTH] if too long, or null if valid.
 */
fun ValidationBuilder<String>.length(
    min: Int,
    max: Int = Int.MAX_VALUE
) = add {
    when {
        it.length < min -> Error.String.MIN_LENGTH
        it.length > max -> Error.String.MAX_LENGTH
        else -> null
    }
}

/**
 * Validates that the string contains a number of digits within the specified range.
 *
 * @param min The minimum number of digits required.
 * @param max The maximum number of digits allowed. Defaults to [Int.MAX_VALUE].
 * @return [Error.String.MIN_DIGIT] or [Error.String.MAX_DIGIT] on failure, null if valid.
 */
fun ValidationBuilder<String>.digit(
    min: Int = 1,
    max: Int = Int.MAX_VALUE
) = add {
    val count = it.count(Char::isDigit)

    when {
        count < min -> Error.String.MIN_DIGIT
        count > max -> Error.String.MAX_DIGIT
        else -> null
    }
}

/**
 * Validates that the string contains a number of special symbols within the specified range.
 *
 * @param min The minimum number of symbols required.
 * @param max The maximum number of symbols allowed. Defaults to [Int.MAX_VALUE].
 * @return [Error.String.MIN_SYMBOL] or [Error.String.MAX_SYMBOL] on failure, null if valid.
 */
fun ValidationBuilder<String>.symbol(
    min: Int = 1,
    max: Int = Int.MAX_VALUE
) = add {
    val count = it.count { c ->
        !c.isLetterOrDigit() && !c.isWhitespace()
    }

    when {
        count < min -> Error.String.MIN_SYMBOL
        count > max -> Error.String.MAX_SYMBOL
        else -> null
    }
}

/**
 * Validates that the string contains an allowed range of uppercase letters.
 *
 * @param min The minimum number of uppercase letters required.
 * @param max The maximum number of uppercase letters allowed. Defaults to [Int.MAX_VALUE].
 * @return [Error.String.MIN_UPPERCASE] or [Error.String.MAX_UPPERCASE] on failure, null if valid.
 */
fun ValidationBuilder<String>.uppercase(
    min: Int = 1,
    max: Int = Int.MAX_VALUE
) = add {
    val count = it.count(Char::isUpperCase)

    when {
        count < min -> Error.String.MIN_UPPERCASE
        count > max -> Error.String.MAX_UPPERCASE
        else -> null
    }
}

/**
 * Validates that the string contains an allowed range of lowercase letters.
 *
 * @param min The minimum number of lowercase letters required.
 * @param max The maximum number of lowercase letters allowed. Defaults to [Int.MAX_VALUE].
 * @return [Error.String.MIN_LOWERCASE] or [Error.String.MAX_LOWERCASE] on failure, null if valid.
 */
fun ValidationBuilder<String>.lowercase(
    min: Int = 1,
    max: Int = Int.MAX_VALUE
) = add {
    val count = it.count(Char::isLowerCase)

    when {
        count < min -> Error.String.MIN_LOWERCASE
        count > max -> Error.String.MAX_LOWERCASE
        else -> null
    }
}