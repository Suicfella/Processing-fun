package processing;

import java.util.Arrays;
import java.util.Random;
import java.util.stream.Collectors;

public class Test {
    public static void main(String[] args) {

        System.out.println();

    }

    public static String accum(String s) {
        StringBuilder sb = new StringBuilder();
        int count = 1;
        for (char c : s.toCharArray()) {

            for (int i = 0; i < count; i++) {
                sb.append(c);
            }
            count++;
        }

        return sb.toString();
    }

    public static String capitalizeEachFirst(String str) {
        return str.substring(0, 1) + str.substring(1);
    }
}
