package exception;

public class MyArrayDataException extends RuntimeException {
    public MyArrayDataException(String s, int i, int j) {
        super("Неправильное значение  " + s + " в массиве  в стобце [" + i + "] строке [" + j + "j");
    }


}
