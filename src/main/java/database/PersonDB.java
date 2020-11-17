package database;

import person.Person;
import java.util.ArrayList;

public class PersonDB extends Database {
    private final ArrayList<Person> db;
    private static PersonDB uniqueDB;

    private PersonDB() {
        this.db = new ArrayList<>();
    }

    public static PersonDB getInstance() {
        if (uniqueDB == null) {
            uniqueDB = new PersonDB();
        }
        return uniqueDB;
    }

    public boolean isInDataBase(Person person){
        return db.contains(person);
    }

    public void addEntry(Person person){
        if(!db.contains(person)){
            db.add(person);
        }
    }
}
