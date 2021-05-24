package entity;

public class Robot implements Motion {
    private String version;
    private int runDistation;
    private int jumpHeigh;


    public Robot(String version, int runDistation, int jumpHeigh) {
        this.version = version;
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

    @Override
    public String toString() {
        return "Robot{" +
                "version='" + version + '\'' +
                ", runDistation=" + runDistation +
                ", jumpHeigh=" + jumpHeigh +
                '}';
    }
}
