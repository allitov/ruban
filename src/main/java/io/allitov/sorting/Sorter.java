package io.allitov.sorting;

import lombok.experimental.UtilityClass;

@UtilityClass
public class Sorter {

    public void bubbleSort(int[] array) {
        for (int i = 0; i < array.length - 1; i++) {
            for (int j = 0; j < array.length - 1 - i; j++) {
                if (array[j] > array[j + 1]) {
                    int temp = array[j];
                    array[j] = array[j + 1];
                    array[j + 1] = temp;
                }
            }
        }
    }

    public static void selectionSort(int[] array) {
        for (int i = 0; i < array.length - 1; i++) {
            int minIndex = i;
            for (int j = i + 1; j < array.length; j++) {
                if (array[j] < array[minIndex]) {
                    minIndex = j;
                }
            }
            int temp = array[i];
            array[i] = array[minIndex];
            array[minIndex] = temp;
        }
    }

    public void mergeSort(int[] array) {
        int n = array.length;
        for (int size = 1; size < n; size *= 2) {
            for (int left = 0; left < n - 1; left += 2 * size) {
                int mid = Math.min(left + size, n);
                int right = Math.min(left + 2 * size, n);
                merge(array, left, mid, right);
            }
        }
    }

    /**
     * Сливает два отсортированных участка массива.
     *
     * @param array массив.
     * @param left начало первого участка.
     * @param mid начало второго участка.
     * @param right конец второго участка.
     */
    private void merge(int[] array, int left, int mid, int right) {
        int[] temp = new int[right - left];
        int i = left;
        int j = mid;
        int k = 0;

        while (i < mid && j < right) {
            if (array[i] <= array[j]) {
                temp[k++] = array[i++];
            } else {
                temp[k++] = array[j++];
            }
        }

        while (i < mid) {
            temp[k++] = array[i++];
        }

        while (j < right) {
            temp[k++] = array[j++];
        }

        System.arraycopy(temp, 0, array, left, temp.length);
    }
}
