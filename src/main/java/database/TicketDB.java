package database;

import ticket.Ticket;
import java.util.HashMap;

public class TicketDB extends Database{
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

    public Ticket getTicket(String name){
        return db.get(name);
    }
}
