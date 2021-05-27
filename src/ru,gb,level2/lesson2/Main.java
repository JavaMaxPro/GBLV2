import exception.MyArrayDataException;
import exception.MyArraySizeException;

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
            System.out.println("Сумма массива = "+sum);
        } catch (MyArraySizeException e) {
            System.out.println("Ошибка размерности массива");
        }catch (MyArrayDataException e){
            System.out.println("Не верные данные в массиве");
        }

    }

    private static int sumArrayV(String[][] mas) {
        proverka(mas);
        int endSum = 0;

        for (int i = 0; i < mas.length; i++) {
            String[] row = mas[i];
            for (int j = 0; j < row.length; j++) {
                String s = row[j];
                try {
                    endSum += Integer.parseInt(s);

                } catch (NumberFormatException e) {
                    throw new MyArrayDataException(s, i, j);
                }
            }
        }
        return endSum;
    }

    private static void proverka(String[][] mas) {
        if (mas.length != ARRAY_SIZE) {
            throw new MyArraySizeException();
        }
        for (String[] row : mas) {
            if (row.length != ARRAY_SIZE) {
                throw new MyArraySizeException();
            }
        }
    }
}