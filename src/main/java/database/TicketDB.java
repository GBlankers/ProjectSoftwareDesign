package database;

import ticket.Ticket;
import java.util.ArrayList;

public class TicketDB extends Database{
    private final ArrayList<Ticket> db;
    private static TicketDB uniqueDB;

    private TicketDB(){
        this.db = new ArrayList<>();
    }

    public static TicketDB getInstance() {
        if(uniqueDB == null){
            uniqueDB = new TicketDB();
        }
        return uniqueDB;
    }
}
