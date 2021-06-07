import java.util.Arrays;

public class lesson5 {
    static final int size = 10000000;
    static final int h = size / 2;

    public static void main(String[] args) throws InterruptedException {

        float[] arr1 = metod1();
        float[] arr2 = metod2();
//        outMas(arr1, arr2);
        System.out.println(Arrays.equals(arr1, arr2));

    }

//    private static void outMas(float[] arr, float[] arr1) {
//        for (int i = 0; i < arr.length; i++)
//            System.out.println(i + "   " + arr[i] + " = " + arr1[i]);
//        System.out.println(arr.length);
//    }

    private static float[] metod1() {

        float[] arr = new float[size];
        for (int i = 0; i < size; i++) {
            arr[i] = 1;
        }
        long a = System.currentTimeMillis();
        for (int i = 0; i < size; i++) {
            arr[i] = (float) (arr[i] * Math.sin(0.2f + i / 5) * Math.cos(0.2f + i / 5) * Math.cos(0.4f + i / 2));
        }
        System.out.println(System.currentTimeMillis() - a);
        return arr;
    }

    private static float[] metod2() throws InterruptedException {
        float[] arr = new float[size];
        float[] a1 = new float[h];
        float[] a2 = new float[h];
        for (int i = 0; i < size; i++) {
            arr[i] = 1;
        }
        long a = System.currentTimeMillis();
        System.arraycopy(arr, 0, a1, 0, h);
        System.arraycopy(arr, h, a2, 0, h);
        Thread thread1 = new Thread(() -> {
            for (int i = 0; i < h; i++)
                a1[i] = (float) (a1[i] * Math.sin(0.2f + i / 5) * Math.cos(0.2f + i / 5) * Math.cos(0.4f + i / 2));
        });
        Thread thread2 = new Thread(() -> {
            for (int i = 0, j = h; i < h; i++, j++)
                a2[i] = (float) (a2[i] * Math.sin(0.2f + j / 5) * Math.cos(0.2f + j / 5) * Math.cos(0.4f + j / 2));
        });
        thread1.start();
        thread2.start();

        thread1.join();
        thread2.join();

        System.arraycopy(a1, 0, arr, 0, h);
        System.arraycopy(a2, 0, arr, h, h);


        System.out.println(System.currentTimeMillis() - a);
        return arr;
    }
}
