package database;

import person.Person;
import ticket.Ticket;
import java.util.HashMap;

public class TicketDB extends Database{
    // name of the ticket + Ticket (contains price and payer)
    private final HashMap<String, Ticket> db;
    private static TicketDB uniqueDB;

    private TicketDB(){
        this.db = new HashMap<>();
    }

    public static TicketDB getInstance() {
        if(uniqueDB == null){
            uniqueDB = new TicketDB();
        }
        return uniqueDB;
    }

    public void addTicket(String name, Ticket ticket){
        this.db.put(name, ticket);
    }

    //When remove ticket is called after a remove person
    public void removeTicketOnly(String ticketName){
        db.remove(ticketName);
    }

    //When a ticket is removed => Person arraylist has to be changed
    public void removeTicket(String ticketName){
        Person payer = db.get(ticketName).getPayer();
        PersonDB.getInstance().removeTicket(payer, ticketName);
    }

    public boolean ticketInDb(String name){
        return db.containsKey(name);
    }

    public Ticket getTicket(String name){
        return db.get(name);
    }
}
