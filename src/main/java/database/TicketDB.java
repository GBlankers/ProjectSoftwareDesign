package database;

import ticket.Ticket;

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

     /*When remove ticket is called after a remove person
     The tickets will already have been removed in the Person Arraylist
     => only remove it in this db */
    public void removeTicketOnly(String ticketName){
        db.remove(ticketName);
    }

    //When a ticket is removed => Person arraylist has to be changed
    public void removeTicket(String ticketName){
        PersonDB.getInstance().removeTicket(db.get(ticketName).getPayer(), ticketName);
    }
}
