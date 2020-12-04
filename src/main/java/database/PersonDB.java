package database;

import person.Person;
import java.util.ArrayList;
import java.util.HashMap;

public class PersonDB extends Database<Person, ArrayList<String>> {

    // Singleton pattern
    private static PersonDB uniqueDB;

    private PersonDB() {
        super();
    }

    public static PersonDB getInstance() {
        if (uniqueDB == null) {
            uniqueDB = new PersonDB();
        }
        return uniqueDB;
    }

    public void addPerson(Person person, ArrayList<String> tickets){
        if(!db.containsKey(person)) {
            db.put(person, new ArrayList<>());
            setChanged();
            notifyObservers(person);
        }
    }

    public void removePerson(Person person){
        ArrayList<String> temp = new ArrayList<>(db.get(person));
        for (String e: temp) {
            TicketDB.getInstance().removeTicketOnly(e);
        }
        db.remove(person);
    }

    public HashMap<Person, ArrayList<String>> getHashMap(){
        return db;
    }

    public void addTicket(Person person, String ticket){
        ArrayList<String> temp;
        temp = db.get(person);
        temp.add(ticket);
        setChanged();
        notifyObservers(ticket);
        db.replace(person, temp);
    }

    public void removeTicket(Person payer, String ticketName){
        ArrayList<String> temp = new ArrayList<>(db.get(payer));
        temp.remove(ticketName);
        db.replace(payer, temp);
    }
}
