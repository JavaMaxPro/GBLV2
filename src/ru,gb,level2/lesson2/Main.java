import exception.MyArraySizeException;

import java.util.Scanner;

public class Main {
    private static final String[][] MAS = new String[][]{
            {"1", "2", "3", "4"},
            {"1", "2", "3", "4"},
            {"1", "2", "3", "4"},
            {"1", "2", "3", "4"},
    };
    private static final int ARRAY_SIZE = 4;


    public static void main(String[] args) {
        try {
            int sum = sumArrayV(MAS);
        } catch (MyArraySizeException e) {
            System.out.println("Ошибка размерности массива");
        }

    }

    private static int sumArrayV(String[][] mas) {
        proverka(mas);
    }

    private static void proverka(String[][] mas) {
        if (mas.length != ARRAY_SIZE) {
            throw new MyArraySizeException();
        }
        for (String[] row : mas
        ) {
            if (row.length != ARRAY_SIZE) {
                throw new MyArraySizeException();
            }
        }
    }
}