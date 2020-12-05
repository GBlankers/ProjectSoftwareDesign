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

    public ticketFactory(){
        this.personDB = PersonDB.getInstance();
        this.ticketDB = TicketDB.getInstance();
    }

    @Override
    public void addTicket(String type, String ticketName, Person payer, double totalPrice) {
        if(type.equals("plane")){
            if(!personDB.inDb(payer)){personDB.addToDb(payer, new ArrayList<>());}
            evenTicket temp = new PlaneTicket(payer, totalPrice);
            personDB.addTicket(payer, ticketName);
            ticketDB.addToDb(ticketName, temp);
        }
    }

    @Override
    public void addTicket(String type, String ticketName, Person payer, HashMap<Person, Double> pricePerPerson) {
        if (type.equals("restaurant")){
            if(!personDB.inDb(payer)){personDB.addToDb(payer, new ArrayList<>());}
            unevenTicket temp = new RestaurantTicket(payer, pricePerPerson);
            personDB.addTicket(payer, ticketName);
            ticketDB.addToDb(ticketName, temp);
        }
    }
}
