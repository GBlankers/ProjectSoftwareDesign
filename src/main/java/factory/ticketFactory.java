package factory;

import database.PersonDB;
import database.TicketDB;
import person.Person;
import ticket.PlaneTicket;
import ticket.RestaurantTicket;
import ticket.evenTicket;
import ticket.unevenTicket;

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
            if(!personDB.inDb(payer)){personDB.addPerson(payer, new ArrayList<>());}
            evenTicket temp = new PlaneTicket(payer, totalPrice);
            personDB.addTicket(payer, ticketName);
            ticketDB.addTicket(ticketName, temp);
        }
    }

    @Override
    public void addTicket(String type, String ticketName, Person payer, HashMap<Person, Double> pricePerPerson) {
        if (type.equals("restaurant")){
            if(!personDB.inDb(payer)){personDB.addPerson(payer, new ArrayList<>());}
            unevenTicket temp = new RestaurantTicket(payer, pricePerPerson);
            personDB.addTicket(payer, ticketName);
            ticketDB.addTicket(ticketName, temp);
        }
    }
}
