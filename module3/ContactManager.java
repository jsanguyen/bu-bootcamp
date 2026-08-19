package Module3;

import java.util.*;

public class ContactManager {
    public static void main(String[] args) {
        HashMap<String, Contact> contacts = new HashMap<>();
        contacts.put("Ada Lovelace", new Contact("Ada Lovelace", "+1 617 555 2345"));
        contacts.put("Grace Hopper", new Contact("Grace Hopper", "+1 617 555 1234"));
        contacts.put("Jonathan Nguyen", new Contact("Jonathan Nguyen", "+1 770 310 1632"));
        contacts.put("Lilibeth Adame", new Contact("Lilibeth Adame", "+1 770 123 4567"));
        contacts.put("John Doe", new Contact("John Doe", "+1 123 456 7890"));

        // Step 5: Make a list of the valid contacts and add 2 non valid ones. 
        List<String> queries = List.of(
                "Ada Lovelace", "Grace Hopper", "Jonathan Nguyen", "Lilibeth Adame", "John Doe",
                "Jane Doe", "Jonathan Win");
        // for each lambda function that runs on each item in the list. 
        queries.forEach(name -> System.out.printf("Looking up \"%s\": %s%n", name,
                Optional.ofNullable(contacts.get(name))
                        .map(Contact::toString)
                        .orElse("Contact not found.")));

        // Step 6: print sorted list
        ArrayList<Contact> sorted = new ArrayList<>(contacts.values());
        sorted.sort((a, b) -> a.getName().compareTo(b.getName()));
        System.out.println("=== All Contacts ===");
        sorted.forEach(System.out::println);
    }
}
