import dev.sn.kvov.core.validate
import dev.sn.kvov.result.Error
import dev.sn.kvov.result.Result
import dev.sn.kvov.types.equal
import dev.sn.kvov.types.even
import dev.sn.kvov.types.negative
import dev.sn.kvov.types.odd
import dev.sn.kvov.types.positive
import dev.sn.kvov.types.range
import org.junit.Test
import kotlin.test.assertEquals

class NumberUnitTest {

    @Test
    fun `range returns Success when number within bounds`() {
        val validation = validate(2025) {
            range(1000..3000)
        }
        assertEquals(
            Result.Success,
            validation
        )
    }

    @Test
    fun `range returns Failure when number is not within bounds`() {
        val validation = validate(2025) {
            range(1000..2000)
        }
        assertEquals(
            Result.Failure(listOf(Error.Number.RANGE)),
            validation
        )
    }

    @Test
    fun `positive returns Success when number is bigger than -1`() {
        val validation = validate(2025) {
            positive()
        }
        assertEquals(
            Result.Success,
            validation
        )
    }

    @Test
    fun `positive returns Failure when number is lower than 0`() {
        val validation = validate(-2025) {
            positive()
        }
        assertEquals(
            Result.Failure(listOf(Error.Number.POSITIVE)),
            validation
        )
    }

    @Test
    fun `negative returns Success when number is lower than 0`() {
        val validation = validate(-2025.45) {
            negative()
        }
        assertEquals(
            Result.Success,
            validation
        )
    }

    @Test
    fun `negative returns Failure when number is bigger than -1`() {
        val validation = validate(134.567) {
            negative()
        }
        assertEquals(
            Result.Failure(listOf(Error.Number.NEGATIVE)),
            validation
        )
    }

    @Test
    fun `even returns Success when there is no remainder after dividing by 2`() {
        val validation = validate(82874.924358) {
            even()
        }
        assertEquals(
            Result.Success,
            validation
        )
    }

    @Test
    fun `even returns Failure when there is remainder after dividing by 2`() {
        val validation = validate(54227) {
            even()
        }
        assertEquals(
            Result.Failure(listOf(Error.Number.EVEN)),
            validation
        )
    }

    @Test
    fun `odd returns Success when there is remainder after dividing by 2`() {
        val validation = validate(53) {
            odd()
        }
        assertEquals(
            Result.Success,
            validation
        )
    }

    @Test
    fun `odd returns Failure when there is no remainder after dividing by 2`() {
        val validation = validate(3026) {
            odd()
        }
        assertEquals(
            Result.Failure(listOf(Error.Number.ODD)),
            validation
        )
    }

    @Test
    fun `equal returns Success when numbers match`() {
        val validation = validate(90099.2433) {
            equal(90099.2433)
        }
        assertEquals(
            Result.Success,
            validation
        )
    }

    @Test
    fun `equal returns Failure when numbers does not match`() {
        val validation = validate(3553) {
            equal(3500)
        }
        assertEquals(
            Result.Failure(listOf(Error.Number.EQUAL)),
            validation
        )
    }

    @Test
    fun `returns Success when all rules are valid`() {
        val validation = validate(2025) {
            range(1..10000)
            positive()
            odd()
            equal(2025)
        }
        assertEquals(
            Result.Success,
            validation
        )
    }

    @Test
    fun `returns Failure with error list when all rules are invalid`() {
        val validation = validate(2025) {
            range(-1000..2000)
            negative()
            even()
            equal(225)
        }
        assertEquals(
            Result.Failure(
                listOf(
                    Error.Number.RANGE,
                    Error.Number.NEGATIVE,
                    Error.Number.EVEN,
                    Error.Number.EQUAL
                )
            ),
            validation
        )
    }
}