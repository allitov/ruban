package io.allitov.backpack;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class BackpackTest {

    @Test
    void shouldFindMaxValue() {
        int[] weights = {1, 3, 4};
        int[] values = {15, 50, 60};

        int maxValue = Backpack.maxValue(weights, values, 8);

        assertThat(maxValue).isEqualTo(130);
    }

    @Test
    void shouldTakeSameItemMultipleTimes() {
        int[] weights = {5};
        int[] values = {10};

        int maxValue = Backpack.maxValue(weights, values, 20);

        assertThat(maxValue).isEqualTo(40);
    }

    @Test
    void shouldReturnZeroWhenNoItemFits() {
        int[] weights = {10};
        int[] values = {100};

        int maxValue = Backpack.maxValue(weights, values, 5);

        assertThat(maxValue).isZero();
    }

    @Test
    void shouldReturnZeroForZeroCapacity() {
        int[] weights = {1};
        int[] values = {10};

        int maxValue = Backpack.maxValue(weights, values, 0);

        assertThat(maxValue).isZero();
    }

    @Test
    void shouldFindOptimalCounts() {
        int[] weights = {2, 3};
        int[] values = {4, 7};

        int[] counts = Backpack.optimalCounts(weights, values, 8);

        assertThat(counts).containsExactly(1, 2);
    }

    @Test
    void shouldFindOptimalCountsForMultipleItems() {
        int[] weights = {1, 3, 4};
        int[] values = {15, 50, 60};

        int[] counts = Backpack.optimalCounts(weights, values, 8);

        assertThat(counts).containsExactly(2, 2, 0);
    }

    @Test
    void shouldReturnEmptyCountsForZeroCapacity() {
        int[] weights = {1, 2};
        int[] values = {10, 20};

        int[] counts = Backpack.optimalCounts(weights, values, 0);

        assertThat(counts).containsExactly(0, 0);
    }

    @ParameterizedTest
    @MethodSource("provideInvalidArguments")
    void shouldThrowWhenArgumentsAreInvalid(int[] weights, int[] values, int capacity) {
        assertThatThrownBy(() -> Backpack.maxValue(weights, values, capacity))
                .isInstanceOf(IllegalArgumentException.class);
        assertThatThrownBy(() -> Backpack.optimalCounts(weights, values, capacity))
                .isInstanceOf(IllegalArgumentException.class);
    }

    private static Stream<Arguments> provideInvalidArguments() {
        return Stream.of(
                Arguments.of(null, new int[]{1}, 1),
                Arguments.of(new int[]{1}, null, 1),
                Arguments.of(new int[]{1, 2}, new int[]{1}, 1),
                Arguments.of(new int[]{1}, new int[]{1}, -1),
                Arguments.of(new int[]{0}, new int[]{1}, 1),
                Arguments.of(new int[]{-1}, new int[]{1}, 1),
                Arguments.of(new int[]{1}, new int[]{-1}, 1)
        );
    }
}
