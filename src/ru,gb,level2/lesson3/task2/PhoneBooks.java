package task2;

import java.util.Set;

public interface PhoneBooks {
    void add(String lastname, String phone);

    Set<String> gete(String lastname);

    Set<String> getAll();


}
