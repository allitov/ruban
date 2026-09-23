package io.allitov.fourier;

import lombok.experimental.UtilityClass;

@UtilityClass
public class Fourier {

    /**
     * Дискретное преобразование Фурье за O(n^2).
     *
     * @param signal вещественный сигнал длины n.
     * @return массив из n комплексных коэффициентов.
     */
    public static Complex[] dft(double[] signal) {
        int n = signal.length;
        Complex[] result = new Complex[n];
        for (int k = 0; k < n; k++) {
            double re = 0;
            double im = 0;
            for (int t = 0; t < n; t++) {
                double angle = 2 * Math.PI * k * t / n;
                re += signal[t] * Math.cos(angle);
                im -= signal[t] * Math.sin(angle);
            }
            result[k] = new Complex(re, im);
        }
        return result;
    }

    public record Complex(double re, double im) {
    }
}
