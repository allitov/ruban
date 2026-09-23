package io.allitov.fourier;

import io.allitov.fourier.Fourier.Complex;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.within;

class FourierTest {

    private static final double EPSILON = 1e-9;

    @ParameterizedTest
    @MethodSource("provideSignalsForDft")
    void dft(double[] input, Complex[] expected) {
        Complex[] actual = Fourier.dft(input);

        assertThat(actual).hasSameSizeAs(expected);
        for (int i = 0; i < expected.length; i++) {
            assertThat(actual[i].re()).isCloseTo(expected[i].re(), within(EPSILON));
            assertThat(actual[i].im()).isCloseTo(expected[i].im(), within(EPSILON));
        }
    }

    private static Stream<Arguments> provideSignalsForDft() {
        return Stream.of(
                Arguments.of(
                        new double[]{},
                        new Complex[]{}
                ),
                Arguments.of(
                        new double[]{5},
                        new Complex[]{new Complex(5, 0)}
                ),
                Arguments.of(
                        new double[]{2, 2, 2, 2},
                        new Complex[]{
                                new Complex(8, 0),
                                new Complex(0, 0),
                                new Complex(0, 0),
                                new Complex(0, 0)
                        }
                ),
                Arguments.of(
                        new double[]{1, 2, 3, 4},
                        new Complex[]{
                                new Complex(10, 0),
                                new Complex(-2, 2),
                                new Complex(-2, 0),
                                new Complex(-2, -2)
                        }
                ),
                Arguments.of(
                        new double[]{1, 2, 3},
                        new Complex[]{
                                new Complex(6, 0),
                                new Complex(-1.5, Math.sqrt(3) / 2),
                                new Complex(-1.5, -Math.sqrt(3) / 2)
                        }
                ),
                Arguments.of(
                        new double[]{1, 0, 0, 0},
                        new Complex[]{
                                new Complex(1, 0),
                                new Complex(1, 0),
                                new Complex(1, 0),
                                new Complex(1, 0)
                        }
                ),
                Arguments.of(
                        new double[]{1, Math.sqrt(2) / 2, 0, -Math.sqrt(2) / 2, -1, -Math.sqrt(2) / 2, 0, Math.sqrt(2) / 2},
                        new Complex[]{
                                new Complex(0, 0),
                                new Complex(4, 0),
                                new Complex(0, 0),
                                new Complex(0, 0),
                                new Complex(0, 0),
                                new Complex(0, 0),
                                new Complex(0, 0),
                                new Complex(4, 0)
                        }
                ),
                Arguments.of(
                        new double[]{0, 1, 0, -1, 0, 1, 0, -1},
                        new Complex[]{
                                new Complex(0, 0),
                                new Complex(0, 0),
                                new Complex(0, -4),
                                new Complex(0, 0),
                                new Complex(0, 0),
                                new Complex(0, 0),
                                new Complex(0, 4),
                                new Complex(0, 0)
                        }
                )
        );
    }
}
