package dev.sn.kvov.result

sealed interface Error {

    enum class String: Error {
        SUBSTRING,
        WHITESPACE,
        MIN_LENGTH,
        MAX_LENGTH,
        MIN_DIGIT,
        MAX_DIGIT,
        MIN_SYMBOL,
        MAX_SYMBOL,
        MIN_UPPERCASE,
        MAX_UPPERCASE,
        MIN_LOWERCASE,
        MAX_LOWERCASE,
    }

    enum class Number: Error {
        RANGE,
        POSITIVE,
        POSITIVE_ZERO,
        NEGATIVE_ZERO,
        NEGATIVE,
        EVEN,
        ODD,
        EQUAL
    }

    enum class Collection: Error {
        MIN_SIZE,
        MAX_SIZE,
        DISTINCT,
        CONTAINS,
        ANY,
        ALL
    }
}