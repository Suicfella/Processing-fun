package processing.algorithmsvisualized;

import processing.core.PApplet;
import processing.data.IntList;

import java.lang.invoke.MethodHandles;
import java.util.Comparator;
import java.util.List;

public class QuickSort extends PApplet {

    public static void main(String[] args) {
        PApplet.main(MethodHandles.lookup().lookupClass());
    }

    private int[] values = new int[100];
    private int[] states = new int[100];

    @Override
    public void settings() {
        size(800, 600);
    }

    @Override
    public void setup() {
        background(0);
        for (int i = 0; i < values.length; i++) {
            values[i] = (int) random(0, width);
            states[i] = -1;
        }

        quickSort(values, 0, values.length - 1);
    }

    @Override
    public void draw() {
        background(0);

        for (int i = 0; i < values.length; i++) {
            noStroke();
            if (states[i] == 0) {
                fill(0xE0777D);
            } else if (states[i] == 1) {
                fill(0xD6FFB7);
            } else {
                fill(255);
            }
            rect(i * 10, height - values[i], 10, values[i]);
        }
    }

    private void quickSort(int[] arr, int start, int end) {
        if (start >= end) {
            return;
        }

        int index = partition(arr, start, end - 1);

        states[index] = -1;

        quickSort(arr, start, index - 1);
        quickSort(arr, index + 1, end);
    }

    private int partition(int[] arr, int start, int end) {
        for (int i = start; i < end; i++) {
            states[i] = 1;
        }

        int pivotValue = arr[end];
        int pivotIndex = start;
        states[pivotIndex] = 0;
        for (int i = start; i < end; i++) {
            if (arr[i] < pivotValue) {
                swap(arr, i, pivotIndex);
                states[pivotIndex] = -1;
                pivotIndex++;
                states[pivotIndex] = 0;
            }
        }
        swap(arr, pivotIndex, end);

        for (int i = start; i < end; i++) {
            if (i != pivotIndex) {
                states[i] = -1;
            }
        }

        return pivotIndex;
    }

    private void swap(int[] arr, int a, int b) {

            int temp = arr[a];
            arr[a] = arr[b];
            arr[b] = temp;

    }

}
