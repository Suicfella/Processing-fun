package processing.lyricsgenerator;

import java.util.function.Predicate;

public class SomeJava {
    public static void main(String[] args) {

    }

    private void mult() {
        Matrix matrix = (a, b) -> a * b;
        System.out.println(matrix.multiply(5, 2));
    }


    private int calculate(int a, int b) {
        return a * b;
    }

    @FunctionalInterface
    public interface Matrix {
        int multiply(int a, int b);
    }

}
