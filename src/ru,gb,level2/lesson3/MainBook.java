import task2.PhoneBook;
import task2.PhoneBooks;

import java.util.Set;

public class MainBook {
    public static void main(String[] args) {

        PhoneBooks phoneBook = new PhoneBook();
        phoneBook.add("Zubar", "456 465");
        phoneBook.add("Loskin", "564 465");
        phoneBook.add("Trutnev", "132 324");
        phoneBook.add("Zubar", "546 987");
        phoneBook.add("Trutnev", "546 546");

        Set<String> allLastname = phoneBook.getAll();
        for (String lastname : allLastname) {
            Set<String> phones = phoneBook.gete(lastname);
            System.out.println(lastname+" - "+phones);
        }

    }
}
