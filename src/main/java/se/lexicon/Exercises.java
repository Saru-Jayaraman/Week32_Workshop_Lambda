package se.lexicon;

import se.lexicon.data.DataStorage;
import se.lexicon.model.Gender;
import se.lexicon.model.Person;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;

public class Exercises {

    private final static DataStorage storage = DataStorage.INSTANCE;

    /*
       TODO:  1.	Find everyone that has firstName: “Erik” using findMany().
    */
    public static void exercise1(String message) {
        System.out.println(message);
        Predicate<Person> predicate = (person) -> person.getFirstName().equalsIgnoreCase("Erik");
        List<Person> result = storage.findMany(predicate);
        System.out.println(result);

        System.out.println("----------------------");
    }

    /*
        TODO:  2.	Find all females in the collection using findMany().
     */
    public static void exercise2(String message) {
        System.out.println(message);
        Predicate<Person> predicate = (person) -> person.getGender().equals(Gender.FEMALE);
        List<Person> result = storage.findMany(predicate);
        System.out.println(result);

        System.out.println("----------------------");
    }

    /*
        TODO:  3.	Find all who are born after (and including) 2000-01-01 using findMany().
     */
    public static void exercise3(String message) {
        System.out.println(message);
        Predicate<Person> predicate = (person) -> person.getBirthDate().isAfter(LocalDate.of(2000,1,1).minusDays(1));
        List<Person> result = storage.findMany(predicate);
        System.out.println(result);

        System.out.println("----------------------");
    }

    /*
        TODO: 4.	Find the Person that has an id of 123 using findOne().
     */
    public static void exercise4(String message) {
        System.out.println(message);
        Predicate<Person> predicate = (person) -> person.getId() == 123;
        Person result = storage.findOne(predicate);
        System.out.println(result);

        System.out.println("----------------------");

    }

    /*
        TODO:  5.	Find the Person that has an id of 456 and convert to String with following content:
            “Name: Nisse Nilsson born 1999-09-09”. Use findOneAndMapToString().
     */
    public static void exercise5(String message) {
        System.out.println(message);
        Predicate<Person> predicate = (person) -> person.getId() == 456;
        Function<Person, String> personToString = (person) -> "Name: ".concat(person.getFirstName()).concat(" ").concat(person.getLastName()).
                    concat(" born ").concat(String.valueOf(person.getBirthDate()));
        String result = storage.findOneAndMapToString(predicate, personToString);
        System.out.println(result);

        System.out.println("----------------------");
    }

    /*
        TODO:  6.	Find all male people whose names start with “E” and convert each to a String using findManyAndMapEachToString().
     */
    public static void exercise6(String message) {
        System.out.println(message);
        Predicate<Person> predicate = (person) -> person.getGender().equals(Gender.MALE) && person.getFirstName().startsWith("E");
        Function<Person, String> personToString = (person) -> person.toString();
        List<String> result = storage.findManyAndMapEachToString(predicate, personToString);
        System.out.println(result);

        System.out.println("----------------------");
    }

    /*
        TODO:  7.	Find all people who are below age of 10 and convert them to a String like this:
            “Olle Svensson 9 years”. Use findManyAndMapEachToString() method.
     */
    public static void exercise7(String message) {
        System.out.println(message);
        Predicate<Person> predicate = (person) -> {
            long age = LocalDate.now().getYear() - person.getBirthDate().getYear();
            return age < 10;
        };
        Function<Person, String> personToString = (person) -> {
            String details = "";
            long age = LocalDate.now().getYear() - person.getBirthDate().getYear();
            details = details.concat(person.getFirstName()).concat(" ").concat(person.getLastName()).
                    concat(" ").concat(String.valueOf(age)).concat(" years");
            return details;
        };
        List<String> result = storage.findManyAndMapEachToString(predicate, personToString);
        System.out.println(result);

        System.out.println("----------------------");
    }

    /*
        TODO:  8.	Using findAndDo() print out all people with firstName “Ulf”.
     */
    public static void exercise8(String message) {
        System.out.println(message);
        Predicate<Person> predicate = (person) -> person.getFirstName().equalsIgnoreCase("Ulf");
        Consumer<Person> consumer = (person) -> System.out.println(person);
        storage.findAndDo(predicate, consumer);

        System.out.println("----------------------");
    }

    /*
        TODO:  9.	Using findAndDo() print out everyone who have their lastName contain their firstName.
     */
    public static void exercise9(String message) {
        System.out.println(message);
        Predicate<Person> predicate = (person) -> person.getLastName().contains(person.getFirstName());
        Consumer<Person> consumer = (person) -> System.out.println(person);
        storage.findAndDo(predicate, consumer);

        System.out.println("----------------------");
    }

    /*
        TODO:  10.	Using findAndDo() print out the firstName and lastName of everyone whose firstName is a palindrome.
     */
    public static void exercise10(String message) {
        System.out.println(message);
        Predicate<Person> predicate = (person) -> {
            StringBuilder sb = new StringBuilder(person.getFirstName());
            sb.reverse();
            return person.getFirstName().equalsIgnoreCase(sb.toString());
        };
        Consumer<Person> consumer = (person) -> System.out.println(person.getFirstName().concat(" ").concat(person.getLastName()));
        storage.findAndDo(predicate, consumer);

        System.out.println("----------------------");
    }

    /*
        TODO:  11.	Using findAndSort() find everyone whose firstName starts with A sorted by birthdate.
     */
    public static void exercise11(String message) {
        System.out.println(message);
        Predicate<Person> predicate = (person) -> person.getFirstName().startsWith("A");
        Comparator<Person> comparator = (o1, o2) -> {
            if(o1.getBirthDate().isAfter(o2.getBirthDate()))
                return 1;
            if(o2.getBirthDate().isAfter(o1.getBirthDate()))
                return -1;
            else
                return 0;
        };
        List<Person> result = storage.findAndSort(predicate, comparator);
        System.out.println(result);

        System.out.println("----------------------");
    }

    /*
        TODO:  12.	Using findAndSort() find everyone born before 1950 sorted reversed by lastest to earliest.
     */
    public static void exercise12(String message) {
        System.out.println(message);
        Predicate<Person> predicate = (person) -> person.getBirthDate().isBefore(LocalDate.of(1950,1,1));
        Comparator<Person> comparator = (o1, o2) -> {
            if(o1.getBirthDate().isBefore(o2.getBirthDate()))
                return 1;
            if(o2.getBirthDate().isBefore(o1.getBirthDate()))
                return -1;
            else
                return 0;
        };
        List<Person> result = storage.findAndSort(predicate, comparator);
        System.out.println(result);

        System.out.println("----------------------");
    }

    /*
        TODO:  13.	Using findAndSort() find everyone sorted in following order: lastName > firstName > birthDate.
     */
    public static void exercise13(String message) {
        System.out.println(message);
        Comparator<Person> comparator = Comparator.comparing(Person::getLastName).thenComparing(Person::getFirstName).
                thenComparing(Person::getBirthDate);
        List<Person> result = storage.findAndSort(comparator);
        System.out.println(result);

        System.out.println("----------------------");
    }
}
