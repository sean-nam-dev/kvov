package dev.sn.kvov.types

import dev.sn.kvov.core.ValidationBuilder
import dev.sn.kvov.result.Error

/**
 * Validates that the size of the collection is within the specified range.
 *
 * @param min The minimum allowed size (inclusive).
 * @param max The maximum allowed size (inclusive). Defaults to [Int.MAX_VALUE].
 * @return [Error.Collection.MIN_SIZE] if the collection is too small,
 * [Error.Collection.MAX_SIZE] if it exceeds the maximum, or null if valid.
 */
fun <T> ValidationBuilder<out Collection<T>>.size(
    min: Int,
    max: Int = Int.MAX_VALUE
) = add {
    when {
        it.size < min -> Error.Collection.MIN_SIZE
        it.size > max -> Error.Collection.MAX_SIZE
        else -> null
    }
}

/**
 * Validates that all elements in the collection are unique.
 *
 * @return [Error.Collection.DISTINCT] if duplicates exist, or null if all elements are unique.
 */
fun <T> ValidationBuilder<out Collection<T>>.distinct() = add {
    if (it.size == it.toSet().size) null
    else Error.Collection.DISTINCT
}

/**
 * Validates that the collection contains all specified [elements].
 *
 * @param elements The elements that must be present in the collection.
 * @return [Error.Collection.CONTAINS] if any element is missing, or null if all are present.
 */
fun <T> ValidationBuilder<out Collection<T>>.contains(
    vararg elements: T
) = add {
    if (elements.isEmpty()) return@add null
    val missing = elements.filterNot(it::contains)
    if (missing.isEmpty()) null else Error.Collection.CONTAINS
}

/**
 * Validates that at least one element in the collection satisfies the given [predicate].
 *
 * @param predicate The condition to test for.
 * @return [Error.Collection.ANY] if no elements match, or null if at least one does.
 */
fun <T> ValidationBuilder<out Collection<T>>.any(
    predicate: (T) -> Boolean
) = add {
    if (it.any(predicate)) null else Error.Collection.ANY
}

/**
 * Validates that all elements in the collection satisfy the given [predicate].
 *
 * @param predicate The condition to test for.
 * @return [Error.Collection.ALL] if any element fails the condition, or null if all satisfy it.
 */
fun <T> ValidationBuilder<out Collection<T>>.all(
    predicate: (T) -> Boolean
) = add {
    if (it.all(predicate)) null else Error.Collection.ALL
}