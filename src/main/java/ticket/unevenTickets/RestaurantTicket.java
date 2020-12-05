package ticket.unevenTickets;

import person.Person;
import ticket.unevenTickets.unevenTicket;

import java.util.HashMap;

public class RestaurantTicket extends unevenTicket {

    public RestaurantTicket(Person payer, HashMap<Person, Double> pricePerPerson){
        super(payer, pricePerPerson);
    }

}
