package person;

public class Person {
    // Name of the person
    private final String name;

    public Person(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    // To easily print a person
    @Override
    public String toString() {
        return name;
    }
}
