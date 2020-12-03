package database;

import person.Person;
import java.util.ArrayList;
import java.util.HashMap;

public class PersonDB extends Database {
    // Collect all persons and the names of the bills they payed
    private final HashMap<Person, ArrayList<String>> db;

    // Singleton pattern
    private static PersonDB uniqueDB;

    private PersonDB() {
        this.db = new HashMap<>();
    }

    public static PersonDB getInstance() {
        if (uniqueDB == null) {
            uniqueDB = new PersonDB();
        }
        return uniqueDB;
    }

    public HashMap<Person, ArrayList<String>> getHashMap(){
        return db;
    }

    public void addPerson(Person person){
        if(!db.containsKey(person)) {
            db.put(person, new ArrayList<>());
            setChanged();
            notifyObservers(person);
        }
    }

    public void removePerson(Person person){
        db.remove(person);
    }

    public void addTicket(Person person, String ticket){
        ArrayList<String> temp;
        temp = db.get(person);
        temp.add(ticket);
        setChanged();
        notifyObservers(ticket);
        db.replace(person, temp);
    }

    public boolean personInDb(Person person){
        return db.getOrDefault(person, null) != null;
    }
}
