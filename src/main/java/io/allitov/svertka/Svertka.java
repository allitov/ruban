package io.allitov.svertka;

import lombok.experimental.UtilityClass;

@UtilityClass
public class Svertka {

    /**
     * Простая (линейная) свертка двух сигналов за O(n*m).
     *
     * @param signal первый сигнал длины n.
     * @param kernel второй сигнал длины m.
     * @return массив длины n + m - 1 со значениями свертки.
     */
    public static double[] convolve(double[] signal, double[] kernel) {
        int n = signal.length;
        int m = kernel.length;
        if (n == 0 || m == 0) {
            return new double[0];
        }
        double[] result = new double[n + m - 1];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                result[i + j] += signal[i] * kernel[j];
            }
        }
        return result;
    }
}
