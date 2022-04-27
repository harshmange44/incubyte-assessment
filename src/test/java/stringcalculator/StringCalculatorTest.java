package stringcalculator;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

public class StringCalculatorTest {

    StringCalculator stringCalculator = new StringCalculator();

    @Test
    public void shouldReturnZeroOnEmptyString() {
        assertEquals(0, stringCalculator.add(""));
    }

    @Test
    public void shouldReturnNumberOnSingleString() {
        assertEquals(10, stringCalculator.add("10"));
    }

    @Test
    public void shouldReturnSumOfNumbersOnTwoStrings() {
        assertEquals(2, stringCalculator.add("1,1"));
    }


    @Test
    public void shouldReturnSumOfAllNumbers() {
        assertEquals(8, stringCalculator.add("1,2,5"));
    }

    @Test
    public void shouldHaveIntegerNumbersException() {
        try {
            stringCalculator.add("10000000000000000000000,2,5");
            fail("Exception expected");
        } catch (RuntimeException e) {
            assertEquals("10000000000000000000000 is not a Integer...", e.getMessage());
        }
    }

    @Test
    public void shouldAllowNewLineAsDelimiter() {
        assertEquals(6, stringCalculator.add("1\n2,3"));
    }

    @Test
    public void shouldAllowCustomDelimiter() {
        assertEquals(3, stringCalculator.add("//<\n1<2"));
    }

    @Test
    public void shouldAllowRegexCharAsCustomDelimiter() {
        assertEquals(13, stringCalculator.add("//;\n1;2;10"));
    }

    @Test
    public void shouldThrowExceptionForNegativeNumbers() {
        try {
            stringCalculator.add("1,-2,3");
            fail("Exception expected");
        } catch (RuntimeException e) {
        }
    }

    @Test
    public void shouldHaveNegativeNumbersInException() {
        try {
            stringCalculator.add("-1,-2,3");
            fail("Exception expected");
        } catch (RuntimeException e) {
            assertEquals("negatives not allowed: -1, -2", e.getMessage());
        }
    }
}
