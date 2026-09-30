package io.allitov.backpack;

import lombok.experimental.UtilityClass;

import java.util.Arrays;

/**
 * Утилитный класс с реализацией алгоритма неограниченного рюкзака:
 * поиск максимальной суммарной стоимости предметов, каждый из которых
 * можно взять неограниченное количество раз, при заданной вместимости.
 */
@UtilityClass
public class Backpack {

    /**
     * Находит максимальную суммарную стоимость предметов, помещающихся в рюкзак.
     * Каждый предмет можно брать произвольное число раз.
     *
     * @param weights  веса предметов
     * @param values   стоимости предметов
     * @param capacity вместимость рюкзака
     * @return максимальная суммарная стоимость предметов
     * @throws IllegalArgumentException если аргументы заданы некорректно
     */
    public int maxValue(int[] weights, int[] values, int capacity) {
        checkArguments(weights, values, capacity);

        int[] dp = new int[capacity + 1];

        for (int i = 0; i < weights.length; i++) {
            for (int w = weights[i]; w <= capacity; w++) {
                dp[w] = Math.max(dp[w], dp[w - weights[i]] + values[i]);
            }
        }

        return dp[capacity];
    }

    /**
     * Находит состав рюкзака с максимальной суммарной стоимостью предметов.
     * Каждый предмет можно брать произвольное число раз.
     *
     * @param weights  веса предметов
     * @param values   стоимости предметов
     * @param capacity вместимость рюкзака
     * @return массив, i-й элемент которого равен количеству копий предмета i
     * в оптимальном решении
     * @throws IllegalArgumentException если аргументы заданы некорректно
     */
    public int[] optimalCounts(int[] weights, int[] values, int capacity) {
        checkArguments(weights, values, capacity);

        int[] dp = new int[capacity + 1];
        int[] choices = new int[capacity + 1];
        Arrays.fill(choices, -1);

        for (int w = 1; w <= capacity; w++) {
            for (int i = 0; i < weights.length; i++) {
                if (weights[i] <= w && dp[w - weights[i]] + values[i] > dp[w]) {
                    dp[w] = dp[w - weights[i]] + values[i];
                    choices[w] = i;
                }
            }
        }

        int[] counts = new int[weights.length];
        int remaining = capacity;
        while (choices[remaining] != -1) {
            int item = choices[remaining];
            counts[item]++;
            remaining -= weights[item];
        }

        return counts;
    }

    /**
     * Проверяет корректность аргументов алгоритма.
     *
     * @param weights  веса предметов
     * @param values   стоимости предметов
     * @param capacity вместимость рюкзака
     */
    private void checkArguments(int[] weights, int[] values, int capacity) {
        if (weights == null || values == null) {
            throw new IllegalArgumentException("Weights and values must not be null");
        }
        if (weights.length != values.length) {
            throw new IllegalArgumentException("Weights and values must have the same length");
        }
        if (capacity < 0) {
            throw new IllegalArgumentException("Capacity must not be negative: " + capacity);
        }

        for (int i = 0; i < weights.length; i++) {
            if (weights[i] <= 0) {
                throw new IllegalArgumentException("Weight must be positive: " + weights[i]);
            }
            if (values[i] < 0) {
                throw new IllegalArgumentException("Value must not be negative: " + values[i]);
            }
        }
    }
}
