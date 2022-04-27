package stringcalculator;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringCalculator {
    private static final Logger logger = LogManager.getLogger(StringCalculator.class);

    /**
     * @param numbersStr
     * @return sum of numbers passed in string
     */
    public int add(String numbersStr) {
        String[] numbers = split(numbersStr);
        int size = numbers.length;
        throwExceptionIfAnyNegative(numbers, size);
        return doSum(numbers, size);
    }

    /**
     * @param numbersStr
     * @return split numbers array
     */
    private String[] split(String numbersStr) {
        if (numbersStr.isEmpty()) {
            return new String[0];
        } else if (isCustomDelimiter(numbersStr)) {
            return splitUsingCustomDelimiter(numbersStr);
        }
        return splitUsingCommaAndNewLine(numbersStr);
    }

    /**
     * @param numbers array of numbers
     * @param size    size of array
     */
    private void throwExceptionIfAnyNegative(String[] numbers, int size) {
        ArrayList<String> negative = new ArrayList<String>();
        for (int i = 0; i < size; i++) {
            if (toInt(numbers[i]) < 0) {
                negative.add(numbers[i]);
            }
        }
        if (negative.size() > 0) {
            throw new RuntimeException("negatives not allowed: " + String.join(", ", negative));
        }
    }

    /**
     * @param numbers array of numbers
     * @param size    size of array
     * @return sum of numbers
     */
    private int doSum(String[] numbers, int size) {
        int sum = 0;
        for (int i = 0; i < size; i++) {
            sum = sum + toInt(numbers[i]);
        }
        return sum;
    }

    /**
     * @param numbersStr
     * @return if custom delim passed
     */
    private boolean isCustomDelimiter(String numbersStr) {
        return numbersStr.startsWith("//");
    }

    /**
     * @param numbersStr
     * @return array split by 'comma'
     */
    private String[] splitUsingCommaAndNewLine(String numbersStr) {
        String[] numArr = numbersStr.split(",|\n");
        return numArr;
    }

    /**
     * @param numbersStr
     * @return array split by custom delimiter
     */
    private String[] splitUsingCustomDelimiter(String numbersStr) {
        final String regex = "\\/\\/(.)\n(.*)";
        final Pattern pattern = Pattern.compile(regex);
        final Matcher matcher = pattern.matcher(numbersStr);

        String customDelimiter = null;
        String numbers = null;
        if (matcher.matches()) {
            logger.info("matches found.. " + matcher.group());
            customDelimiter = matcher.group(1);
            numbers = matcher.group(2);
        }
        if (customDelimiter == null) {
            logger.error("Executing 'splitUsingCustomDelimiter': Couldn't find 'customDelimiter'...");
            throw new RuntimeException("Couldn't find 'customDelimiter'...");
        }
        if (numbers == null) {
            logger.error("Executing 'splitUsingCustomDelimiter': Couldn't find 'numbers'...");
            throw new RuntimeException("Couldn't find 'numbers'...");
        }
        return numbers.split(customDelimiter);
    }

    /**
     * @param numberStr
     * @return convert string num to int
     */
    private int toInt(String numberStr) {
        int intNumber;
        try {
            intNumber = Integer.parseInt(numberStr);
        } catch (NumberFormatException e) {
            logger.error(numberStr + " is not a Integer...");
            throw new RuntimeException(numberStr + " is not a Integer...");
        }
        return intNumber;
    }
}
