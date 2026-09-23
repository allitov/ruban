package io.allitov.svertka;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.within;

class SvertkaTest {

    private static final double EPSILON = 1e-9;

    @ParameterizedTest
    @MethodSource("provideSignalsForConvolve")
    void convolve(double[] signal, double[] kernel, double[] expected) {
        double[] actual = Svertka.convolve(signal, kernel);

        assertThat(actual).containsExactly(expected, within(EPSILON));
    }

    private static Stream<Arguments> provideSignalsForConvolve() {
        return Stream.of(
                Arguments.of(
                        new double[]{},
                        new double[]{},
                        new double[]{}
                ),
                Arguments.of(
                        new double[]{1, 2, 3},
                        new double[]{},
                        new double[]{}
                ),
                Arguments.of(
                        new double[]{},
                        new double[]{1, 2},
                        new double[]{}
                ),
                Arguments.of(
                        new double[]{5},
                        new double[]{3},
                        new double[]{15}
                ),
                Arguments.of(
                        new double[]{1, 2},
                        new double[]{3, 4},
                        new double[]{3, 10, 8}
                ),
                Arguments.of(
                        new double[]{1, 2, 3},
                        new double[]{4, 5},
                        new double[]{4, 13, 22, 15}
                ),
                Arguments.of(
                        new double[]{1, 2, 3},
                        new double[]{1},
                        new double[]{1, 2, 3}
                ),
                Arguments.of(
                        new double[]{1, 2, 3},
                        new double[]{0, 1, 0},
                        new double[]{0, 1, 2, 3, 0}
                ),
                Arguments.of(
                        new double[]{-1, 2},
                        new double[]{3, -4},
                        new double[]{-3, 10, -8}
                ),
                Arguments.of(
                        new double[]{0.5, 0.5},
                        new double[]{2, 4},
                        new double[]{1, 3, 2}
                ),
                Arguments.of(
                        new double[]{0, 0},
                        new double[]{1, 2, 3},
                        new double[]{0, 0, 0, 0}
                )
        );
    }
}
