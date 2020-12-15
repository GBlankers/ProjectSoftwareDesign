package ticket.unevenTickets;

import person.Person;

import java.util.HashMap;

public class BarTicket extends unevenTicket{

    public BarTicket(Person payer, HashMap<Person, Double> pricePerPerson) {
        super(payer, pricePerPerson);
    }

}
