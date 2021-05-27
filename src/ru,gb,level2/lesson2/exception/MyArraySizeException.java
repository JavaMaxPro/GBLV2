package exception;

public class MyArraySizeException extends  RuntimeException {
    public MyArraySizeException(){
        super("Ошибка размерности массива");
    }
}
