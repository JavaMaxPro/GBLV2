package entity;

public class Cat {
    private String name;
    private String age;
    private int runDistation;
    private int jumpHigh;

    public Cat(String name, String age) {
        this.name = name;
        this.age = age;
    }

    public Cat(String name, String age, int runDistation, int jumpHigh) {
        this(name,age);
        this.runDistation = runDistation;
        this.jumpHigh = jumpHigh;
    }
}
