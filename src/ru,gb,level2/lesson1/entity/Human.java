package entity;

public class Human {
    private String firstName;
    private String lastName;
    private int age;
    private int runDistation;
    private int jumpHigh;

    public Human(String firstName,String lastName,int age ) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
    }
    public Human(String firstName,String lastName,int age,int runDistation,int jumpHigh ){
        this(firstName,lastName,age);
        this.runDistation=runDistation;
        this.jumpHigh=jumpHigh;
    }

}
