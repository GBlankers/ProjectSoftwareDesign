package ticket.unevenTickets;

import person.Person;
import ticket.Ticket;

import java.util.HashMap;

public abstract class unevenTicket extends Ticket {
    private final HashMap<Person, Double> pricePerPerson;

    public unevenTicket(Person payer, HashMap<Person, Double> pricePerPerson) {
        super(payer);
        this.pricePerPerson = pricePerPerson;
    }

    public HashMap<Person, Double> getPricePerPerson() {
        return pricePerPerson;
    }

}
