package exception;

public class MyArrayDataException extends RuntimeException {
    public MyArrayDataException(String s, int i, int j) {
        super("Неправильное значение символа [" + s + "]  массива  в стобце [" + i + "] строке [" + j + "]");
    }


}
