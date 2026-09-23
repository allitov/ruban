package io.allitov.stolbik;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;

class StolbikTest {

    @ParameterizedTest
    @MethodSource("provideNumbersForMultiply")
    void multiply(String first, String second, String expected) {
        String actual = Stolbik.multiply(first, second);

        assertThat(actual).isEqualTo(expected);
    }

    @Test
    void multiplyIsCommutative() {
        String firstProduct = Stolbik.multiply("123456789", "987654321");
        String secondProduct = Stolbik.multiply("987654321", "123456789");

        assertThat(firstProduct).isEqualTo(secondProduct).isEqualTo("121932631112635269");
    }

    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = {" ", "12a", "1.5", "-1", "+1", "١٢"})
    void multiplyRejectsInvalidNumber(String invalidNumber) {
        assertThatIllegalArgumentException()
                .isThrownBy(() -> Stolbik.multiply(invalidNumber, "1"))
                .withMessage("Number must be a non-empty decimal number: " + invalidNumber);
    }

    private static Stream<Arguments> provideNumbersForMultiply() {
        return Stream.of(
                Arguments.of("0", "0", "0"),
                Arguments.of("0", "123", "0"),
                Arguments.of("123", "0", "0"),
                Arguments.of("00", "000123", "0"),
                Arguments.of("7", "8", "56"),
                Arguments.of("1", "12345", "12345"),
                Arguments.of("12345", "1", "12345"),
                Arguments.of("99", "99", "9801"),
                Arguments.of("123", "456", "56088"),
                Arguments.of("999", "999", "998001"),
                Arguments.of("123456789", "987654321", "121932631112635269"),
                Arguments.of(
                        "123456789012345678901234567890",
                        "987654321098765432109876543210",
                        "121932631137021795226185032733622923332237463801111263526900"
                )
        );
    }
}
