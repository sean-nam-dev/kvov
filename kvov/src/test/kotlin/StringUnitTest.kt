import dev.sn.kvov.core.validate
import dev.sn.kvov.result.Error
import dev.sn.kvov.result.Result
import dev.sn.kvov.types.digit
import dev.sn.kvov.types.length
import dev.sn.kvov.types.lowercase
import dev.sn.kvov.types.substring
import dev.sn.kvov.types.symbol
import dev.sn.kvov.types.uppercase
import dev.sn.kvov.types.whitespace
import org.junit.Test
import kotlin.test.assertEquals

class StringUnitTest {

    @Test
    fun `substring should return Success when sub appears in text`() {
        val validation = validate("Some text to test") {
            substring("text to")
        }
        assertEquals(
            Result.Success,
            validation
        )
    }

    @Test
    fun `substring should return Failure when sub does not appears in text`() {
        val validation = validate("Some text to test") {
            substring("kotlin")
        }
        assertEquals(
            Result.Failure(listOf(Error.String.SUBSTRING)),
            validation
        )
    }

    @Test
    fun `whitespace should return Success when allow=true and space appears in text`() {
        val validation = validate("Some text to test") {
            whitespace(true)
        }
        assertEquals(
            Result.Success,
            validation
        )
    }

    @Test
    fun `whitespace should return Failure when allow=false and space appears in text`() {
        val validation = validate("Some text to test") {
            whitespace(false)
        }
        assertEquals(
            Result.Failure(listOf(Error.String.WHITESPACE)),
            validation
        )
    }

    @Test
    fun `length should return Success when text length is between min and max`() {
        val validation = validate("Some text to test") {
            length(
                min = 5,
                max = 20
            )
        }
        assertEquals(
            Result.Success,
            validation
        )
    }

    @Test
    fun `length should return Failure when text length is less than min`() {
        val validation = validate("Some text to test") {
            length(min = 20)
        }
        assertEquals(
            Result.Failure(listOf(Error.String.MIN_LENGTH)),
            validation
        )
    }

    @Test
    fun `length should return Failure when text length is bigger than max`() {
        val validation = validate("Some text to test") {
            length(
                min = 1,
                max = 10
            )
        }
        assertEquals(
            Result.Failure(listOf(Error.String.MAX_LENGTH)),
            validation
        )
    }

    @Test
    fun `digit returns Success when number count is between min and max`() {
        val validation = validate("Some1 text2 to3 test4") {
            digit(
                min = 3,
                max = 10
            )
        }
        assertEquals(
            Result.Success,
            validation
        )
    }

    @Test
    fun `digit returns Failure when number count is less than min`() {
        val validation = validate("Some1 text2 to3 test4") {
            digit(min = 5)
        }
        assertEquals(
            Result.Failure(listOf(Error.String.MIN_DIGIT)),
            validation
        )
    }

    @Test
    fun `digit returns Failure when number count is bigger than max`() {
        val validation = validate("Some1 text2 to3 test4") {
            digit(max = 3)
        }
        assertEquals(
            Result.Failure(listOf(Error.String.MAX_DIGIT)),
            validation
        )
    }

    @Test
    fun `symbol returns Success when symbol number is between min and max`() {
        val validation = validate("Some$ text% to@ test!") {
            symbol(
                min = 2,
                max = 10
            )
        }
        assertEquals(
            Result.Success,
            validation
        )
    }

    @Test
    fun `symbol returns Failure when symbol number is less than min`() {
        val validation = validate("Some$ text% to@ test!") {
            symbol(min = 5)
        }
        assertEquals(
            Result.Failure(listOf(Error.String.MIN_SYMBOL)),
            validation
        )
    }

    @Test
    fun `symbol returns Failure when symbol number is bigger than max`() {
        val validation = validate("Some$ text% to@ test!") {
            symbol(max = 2)
        }
        assertEquals(
            Result.Failure(listOf(Error.String.MAX_SYMBOL)),
            validation
        )
    }

    @Test
    fun `uppercase returns Success when uppercase number is between min and max`() {
        val validation = validate("Some Text To Test") {
            uppercase(
                min = 3,
                max = 15
            )
        }
        assertEquals(
            Result.Success,
            validation
        )
    }

    @Test
    fun `uppercase returns Failure when uppercase number is lower than min`() {
        val validation = validate("Some Text To Test") {
            uppercase(min = 7)
        }
        assertEquals(
            Result.Failure(listOf(Error.String.MIN_UPPERCASE)),
            validation
        )
    }

    @Test
    fun `uppercase returns Failure when uppercase number is bigger than max`() {
        val validation = validate("Some Text To Test") {
            uppercase(max = 2)
        }
        assertEquals(
            Result.Failure(listOf(Error.String.MAX_UPPERCASE)),
            validation
        )
    }

    @Test
    fun `lowercase returns Success when lowercase number is between min and max`() {
        val validation = validate("Some text to test") {
            lowercase(
                min = 5,
                max = 30
            )
        }
        assertEquals(
            Result.Success,
            validation
        )
    }

    @Test
    fun `lowercase returns Failure when lowercase number is lower than min`() {
        val validation = validate("Some text to test") {
            lowercase(min = 15)
        }
        assertEquals(
            Result.Failure(listOf(Error.String.MIN_LOWERCASE)),
            validation
        )
    }

    @Test
    fun `lowercase returns Failure when lowercase number is bigger than max`() {
        val validation = validate("Some text to test") {
            lowercase(max = 5)
        }
        assertEquals(
            Result.Failure(listOf(Error.String.MAX_LOWERCASE)),
            validation
        )
    }

    @Test
    fun `returns Success when all rules are valid`() {
        val validation = validate("1#Some 2!Text 3^to 4&tesT") {
            substring("3^to")
            whitespace(true)
            length(min = 10, max = 50)
            digit(min = 2, max = 10)
            symbol(min = 4, max = 10)
            uppercase(min = 3, max = 10)
            lowercase(min = 10, max = 20)
        }
        assertEquals(
            Result.Success,
            validation
        )
    }

    @Test
    fun `returns Failure with list of errors when all rules are invalid`() {
        val validation = validate("1#Some 2!Text 3^to 4&tesT") {
            substring("kotlin")
            whitespace(false)
            length(min = 50)
            digit(min = 10)
            symbol(max = 1)
            uppercase(max = 1)
            lowercase(max = 5)
        }
        assertEquals(
            Result.Failure(
                listOf(
                    Error.String.SUBSTRING,
                    Error.String.WHITESPACE,
                    Error.String.MIN_LENGTH,
                    Error.String.MIN_DIGIT,
                    Error.String.MAX_SYMBOL,
                    Error.String.MAX_UPPERCASE,
                    Error.String.MAX_LOWERCASE
                )
            ),
            validation
        )
    }
}