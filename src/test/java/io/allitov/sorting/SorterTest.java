package io.allitov.sorting;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

class SorterTest {

    @ParameterizedTest
    @MethodSource("provideArraysForSorting")
    void bubbleSort(int[] input, int[] expected) {
        Sorter.bubbleSort(input);

        assertThat(input).containsExactly(expected);
    }

    private static Stream<Arguments> provideArraysForSorting() {
        return Stream.of(
                Arguments.of(
                        new int[]{0, -3, 2, -1, 0, 4},
                        new int[]{-3, -1, 0, 0, 2, 4}
                ),
                Arguments.of(
                        new int[]{5, 4, 3, 2, 1},
                        new int[]{1, 2, 3, 4, 5}
                ),
                Arguments.of(
                        new int[]{1, 2, 3, 4, 5},
                        new int[]{1, 2, 3, 4, 5}
                ),
                Arguments.of(
                        new int[]{42},
                        new int[]{42}
                ),
                Arguments.of(
                        new int[]{},
                        new int[]{}
                ),
                Arguments.of(
                        new int[]{7, 7, 7, 7},
                        new int[]{7, 7, 7, 7}
                )
        );
    }
}