package net.pcpinfo.jbcodetest.unittest.util;

import net.pcpinfo.jbcodetest.util.FizzBuzzUtils;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.assertThat;

public class FizzBuzzUtilsTest {

    @ParameterizedTest
    @ValueSource(ints = { 1, 2, 7, 11, 77, 7211, 7919})
    public void should_return_number_value_when_is_no_multiple_of_five_and_three(int input) {
        assertThat(FizzBuzzUtils.fizzBuzzNumber(input)).isEqualTo(String.valueOf(input));
    }

    @ParameterizedTest
    @ValueSource(ints = { 3, 9, 33, 66, 999, 1077, 123321 })
    public void should_return_fizz_when_multiple_of_three_and_not_five(int input) {
        assertThat(FizzBuzzUtils.fizzBuzzNumber(input)).isEqualTo("fizz");
    }

    @ParameterizedTest
    @ValueSource(ints = { 5, 10, 20, 500, 1085, 542300 })
    public void should_return_fizz_when_multiple_of_five_and_not_three(int input) {
        assertThat(FizzBuzzUtils.fizzBuzzNumber(input)).isEqualTo("buzz");
    }

    @ParameterizedTest
    @ValueSource(ints = { 15, 45, 900, 1080, 12315, 1233210 })
    public void should_return_fizzbuzz_when_multiple_of_five_and_three(int input) {
        assertThat(FizzBuzzUtils.fizzBuzzNumber(input)).isEqualTo("fizzbuzz");
    }
}
