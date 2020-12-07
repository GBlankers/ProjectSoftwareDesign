package database;

import person.Person;
import java.util.ArrayList;

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

    // Remove a person from the db + remove all its tickets from the ticket db
    public void removePerson(Person person){
        // If a person is removed, all the tickets of this person need to be removed as well
        ArrayList<String> temp = new ArrayList<>(db.get(person));
        for (String e: temp) {
            TicketDB.getInstance().removeTicketOnly(e);
        }

        // Remove from person db
        db.remove(person);
    }

    // Add a ticket to a person in the db
    public void addTicket(Person person, String ticket){
        // Add the ticket to the existing Arraylist
        ArrayList<String> temp = new ArrayList<>(db.get(person));
        temp.add(ticket);

        //Observer
        setChanged();
        notifyObservers(ticket);

        // Replace the old ticket list with the new one
        db.replace(person, temp);
    }

    // Remove a ticket from a persons list
    public void removeTicket(Person payer, String ticketName){
        // remove ticket from list in personDb
        ArrayList<String> temp = new ArrayList<>(db.get(payer));
        temp.remove(ticketName);
        db.replace(payer, temp);

        // Remove ticket from Ticket db
        TicketDB.getInstance().removeTicketOnly(ticketName);
    }
}
