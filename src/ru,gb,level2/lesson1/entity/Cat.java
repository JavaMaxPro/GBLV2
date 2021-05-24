package entity;

public class Cat implements Motion{
    private String name;
    private int runDistation;
    private int jumpHeigh;

    public Cat(String name,  int runDistation, int jumpHeigh) {
        this.name = name;
        this.runDistation = runDistation;
        this.jumpHeigh = jumpHeigh;
    }

    @Override
    public int run() {
        return runDistation;
    }

    @Override
    public int jump() {
        return jumpHeigh;
    }
}
