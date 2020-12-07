package factory;

import database.PersonDB;
import database.TicketDB;
import person.Person;
import ticket.evenTickets.PlaneTicket;
import ticket.unevenTickets.RestaurantTicket;
import ticket.evenTickets.evenTicket;
import ticket.unevenTickets.unevenTicket;

import java.util.ArrayList;
import java.util.HashMap;

public class ticketFactory implements ticketFactoryAbstract{
    private final PersonDB personDB;
    private final TicketDB ticketDB;

    // initialize dbs
    public ticketFactory(){
        this.personDB = PersonDB.getInstance();
        this.ticketDB = TicketDB.getInstance();
    }

    // If the tickets is an even ticket
    @Override
    public void addTicket(String type, String ticketName, Person payer, double totalPrice) throws RuntimeException{
        // Check for existing type of ticket
        if(type.equals("plane")){
            // If the payer does not exist => add it to db
            if(!personDB.inDb(payer)){personDB.addToDb(payer, new ArrayList<>());}

            // make a new ticket + add to dbs
            evenTicket temp = new PlaneTicket(payer, totalPrice);
            personDB.addTicket(payer, ticketName);
            ticketDB.addToDb(ticketName, temp);
        } else {
            throw new RuntimeException("Unknown ticket type " + type);
        }
    }

    // If the ticket is an uneven ticket
    @Override
    public void addTicket(String type, String ticketName, Person payer, HashMap<Person, Double> pricePerPerson) throws RuntimeException{
        // Check for existing type of ticket
        if (type.equals("restaurant")){
            // If the payer does not exist => add it to db
            if(!personDB.inDb(payer)){personDB.addToDb(payer, new ArrayList<>());}

            // make a new ticket + add to dbs
            unevenTicket temp = new RestaurantTicket(payer, pricePerPerson);
            personDB.addTicket(payer, ticketName);
            ticketDB.addToDb(ticketName, temp);
        } else {
            throw new RuntimeException("Unknown ticket type");
        }
    }
}
