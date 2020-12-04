package database;

import person.Person;
import ticket.Ticket;
import java.util.HashMap;

public class TicketDB extends Database<String, Ticket>{

    //Singleton pattern
    private static TicketDB uniqueDB;

    private TicketDB(){
        super();
    }

    public static TicketDB getInstance() {
        if(uniqueDB == null){
            uniqueDB = new TicketDB();
        }
        return uniqueDB;
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

    public Ticket getTicket(String name){
        return db.get(name);
    }
}
