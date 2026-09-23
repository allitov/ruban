package io.allitov.stolbik;

import lombok.experimental.UtilityClass;

@UtilityClass
public class Stolbik {

    /**
     * Умножает натуральные десятичные числа столбиком и возвращает результат строкой.
     * Ведущие нули в результате отбрасываются.
     *
     * @param first  первый множитель в десятичной записи
     * @param second второй множитель в десятичной записи
     * @return произведение чисел в десятичной записи
     */
    public String multiply(String first, String second) {
        int[] left = toDigits(first);
        int[] right = toDigits(second);
        int[] result = new int[left.length + right.length];

        for (int i = left.length - 1; i >= 0; i--) {
            for (int j = right.length - 1; j >= 0; j--) {
                int product = left[i] * right[j] + result[i + j + 1];
                result[i + j + 1] = product % 10;
                result[i + j] += product / 10;
            }
        }

        int significant = 0;
        while (significant < result.length - 1 && result[significant] == 0) {
            significant++;
        }

        StringBuilder product = new StringBuilder(result.length - significant);
        for (int i = significant; i < result.length; i++) {
            product.append((char) ('0' + result[i]));
        }

        return product.toString();
    }

    private int[] toDigits(String number) {
        if (number == null
                || number.isBlank()
                || !number.chars().allMatch(digit -> digit >= '0' && digit <= '9')) {
            throw new IllegalArgumentException("Number must be a non-empty decimal number: " + number);
        }

        int[] digits = new int[number.length()];
        for (int i = 0; i < number.length(); i++) {
            digits[i] = number.charAt(i) - '0';
        }

        return digits;
    }
}
