package io.allitov.svertka;

import io.allitov.fourier.Fourier;
import lombok.experimental.UtilityClass;

@UtilityClass
public class SvertkaFourier {

    /**
     * Линейная свертка двух сигналов через дискретное преобразование Фурье за O(n^2).
     *
     * @param signal первый сигнал длины n.
     * @param kernel второй сигнал длины m.
     * @return массив длины n + m - 1 со значениями свертки.
     */
    public double[] convolve(double[] signal, double[] kernel) {
        int n = signal.length;
        int m = kernel.length;
        if (n == 0 || m == 0) {
            return new double[0];
        }

        int length = n + m - 1;
        Fourier.Complex[] signalSpectrum = Fourier.dft(pad(signal, length));
        Fourier.Complex[] kernelSpectrum = Fourier.dft(pad(kernel, length));

        Fourier.Complex[] convolutionSpectrum = new Fourier.Complex[length];
        for (int i = 0; i < length; i++) {
            double re = signalSpectrum[i].re() * kernelSpectrum[i].re()
                    - signalSpectrum[i].im() * kernelSpectrum[i].im();
            double im = signalSpectrum[i].re() * kernelSpectrum[i].im()
                    + signalSpectrum[i].im() * kernelSpectrum[i].re();
            convolutionSpectrum[i] = new Fourier.Complex(re, im);
        }

        return inverseDft(convolutionSpectrum);
    }

    /**
     * Обратное дискретное преобразование Фурье за O(n^2).
     *
     * @param spectrum массив из n комплексных коэффициентов.
     * @return массив из n вещественных значений сигнала.
     */
    private double[] inverseDft(Fourier.Complex[] spectrum) {
        int n = spectrum.length;
        double[] result = new double[n];
        for (int t = 0; t < n; t++) {
            double re = 0;
            double im = 0;
            for (int k = 0; k < n; k++) {
                double angle = 2 * Math.PI * k * t / n;
                re += spectrum[k].re() * Math.cos(angle)
                        - spectrum[k].im() * Math.sin(angle);
                im += spectrum[k].re() * Math.sin(angle)
                        + spectrum[k].im() * Math.cos(angle);
            }
            result[t] = re / n;
        }
        return result;
    }

    private double[] pad(double[] signal, int length) {
        double[] padded = new double[length];
        System.arraycopy(signal, 0, padded, 0, signal.length);
        return padded;
    }
}
