import dev.sn.kvov.core.validate
import dev.sn.kvov.result.Error
import dev.sn.kvov.result.Result
import dev.sn.kvov.types.all
import dev.sn.kvov.types.any
import dev.sn.kvov.types.contains
import dev.sn.kvov.types.distinct
import dev.sn.kvov.types.size
import org.junit.Test
import kotlin.test.assertEquals

class CollectionUnitTest {

    @Test
    fun `size should return Success when size is between min and max`() {
        val validation = validate(setOf(1,2,3,4,5,6,7,8,9,0)) {
            size(
                min = 5,
                max = 10
            )
        }
        assertEquals(
            Result.Success,
            validation
        )
    }

    @Test
    fun `size should return Failure when size is lower than min`() {
        val validation = validate(setOf(1,2,3,4,5,6,7,8,9,0)) {
            size(min = 15)
        }
        assertEquals(
            Result.Failure(listOf(Error.Collection.MIN_SIZE)),
            validation
        )
    }

    @Test
    fun `size should return Failure when size is bigger than max`() {
        val validation = validate(setOf(1,2,3,4,5,6,7,8,9,0)) {
            size(
                min = 1,
                max = 5
            )
        }
        assertEquals(
            Result.Failure(listOf(Error.Collection.MAX_SIZE)),
            validation
        )
    }

    @Test
    fun `distinct should return Success when all values are unique`() {
        val validation = validate(listOf('a', 'b', 'c', 'd', 'e')) {
            distinct()
        }
        assertEquals(
            Result.Success,
            validation
        )
    }

    @Test
    fun `distinct should return Failure when not all values are unique`() {
        val validation = validate(listOf('a', 'a', 'c', 'd', 'e')) {
            distinct()
        }
        assertEquals(
            Result.Failure(listOf(Error.Collection.DISTINCT)),
            validation
        )
    }

    @Test
    fun `contains should return Success when element(s) appear in collection`() {
        val validation = validate(listOf("James" to 21, "Oliver" to 30)) {
            contains("James" to 21, "Oliver" to 30)
        }
        assertEquals(
            Result.Success,
            validation
        )
    }

    @Test
    fun `contains should return Failure when element(s) do not appear in collection`() {
        val validation = validate(listOf("James" to 21, "Oliver" to 30)) {
            contains("Mike" to 24)
        }
        assertEquals(
            Result.Failure(listOf(Error.Collection.CONTAINS)),
            validation
        )
    }

    @Test
    fun `any should return Success when at least one element meets predicate`() {
        val validation = validate(setOf(10.25, 77.33, 94.12, 59.72)) {
            any { it > 5 }
        }
        assertEquals(
            Result.Success,
            validation
        )
    }

    @Test
    fun `any should return Failure when appears no element with given predicate`() {
        val validation = validate(setOf(10.25, 77.33, 94.12, 59.72)) {
            any { it > 100}
        }
        assertEquals(
            Result.Failure(listOf(Error.Collection.ANY)),
            validation
        )
    }

    @Test
    fun `all should return Success when all elements meet predicate`() {
        val validation = validate(listOf("Cat", "Cow", "Camel", "Crow")) {
            all { it.startsWith("C") }
        }
        assertEquals(
            Result.Success,
            validation
        )
    }

    @Test
    fun `all should return Failure when not all elements meet predicate`() {
        val validation = validate(listOf("Cat", "Cow", "Camel", "Crow")) {
            all { it.endsWith("qwerty") }
        }
        assertEquals(
            Result.Failure(listOf(Error.Collection.ALL)),
            validation
        )
    }

    @Test
    fun `should return Success when all rules are valid`() {
        val validation = validate(listOf(10, 20, 30, 40, 50)) {
            size(
                min = 2,
                max = 10
            )
            distinct()
            contains(30, 50)
            any { it % 2 == 0 }
            all { it > 5 }
        }
        assertEquals(
            Result.Success,
            validation
        )
    }

    @Test
    fun `should return Failure when all rules are invalid`() {
        val validation = validate(listOf(10, 10, 30, 40, 50)) {
            size(min = 10,)
            distinct()
            contains(2, 4, 6, 8)
            any { it % 2 != 0 }
            all { it == 5 }
        }
        assertEquals(
            Result.Failure(
                listOf(
                    Error.Collection.MIN_SIZE,
                    Error.Collection.DISTINCT,
                    Error.Collection.CONTAINS,
                    Error.Collection.ANY,
                    Error.Collection.ALL
                )
            ),
            validation
        )
    }
}