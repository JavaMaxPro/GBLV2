public class Robot {
    private String name;
    private int version;
    private int runDistation;
    private int jumpHigh;

    public Robot(String name,int version){
        this.name=name;
        this.version=version;
    }
    public Robot(String name,int version,int runDistation, int jumpHigh){
        this(name,version);
        this.runDistation = runDistation;
        this.jumpHigh=jumpHigh;
    }
}
