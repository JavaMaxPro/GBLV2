package task2;

import java.util.*;

public class PhoneBook implements PhoneBooks {
    Map<String, Set<String>> book;

    public PhoneBook() {
        book = new TreeMap<>();
    }

    @Override
    public void add(String lastname, String phone) {
        Set<String> phones = getPhone(lastname);
        phones.add(phone);
    }

    @Override
    public Set<String> gete(String lastname) {
        return getPhone(lastname);
    }

    private Set<String> getPhone(String lastname) {
        Set<String> phones = book.getOrDefault(lastname, new HashSet<>());
        if (phones.isEmpty()) {
            book.put(lastname, phones);
        }
        return phones;
    }


    @Override
    public Set<String> getAll() {
        return book.keySet();
    }
}
