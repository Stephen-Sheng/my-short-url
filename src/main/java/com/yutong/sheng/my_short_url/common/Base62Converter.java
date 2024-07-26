package com.yutong.sheng.my_short_url.common;

/**
 * @author yutongsheng
 */
public class Base62Converter {
    public static String convertToBase62(long number) {
        if (number == 0) {
            return "0";
        }

        StringBuilder result = new StringBuilder();
        while (number > 0) {
            long remainder = number % 62;
            char digit = (char) (remainder + '0');
            if (digit > '9') {
                digit = (char) (remainder - 10 + 'A');
            }
            if (digit > 'Z') {
                digit = (char) (remainder - 36 + 'a');
            }
            result.append(digit);
            number /= 62;
        }

        return result.reverse().toString();
    }
}