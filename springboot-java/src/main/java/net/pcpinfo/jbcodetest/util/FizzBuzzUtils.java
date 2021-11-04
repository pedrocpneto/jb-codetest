package net.pcpinfo.jbcodetest.util;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class FizzBuzzUtils {

    public static String fizzBuzzNumber(int number) {
        var result = "";
        if (number % 3 == 0) {
            result += "fizz";
        }
        if (number % 5 == 0) {
            result += "buzz";
        }
        return "".equals(result) ? String.valueOf(number) : result;
    }

}
