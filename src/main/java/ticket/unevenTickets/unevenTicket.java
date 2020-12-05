package ticket.unevenTickets;

import person.Person;
import ticket.Ticket;

import java.util.HashMap;

public abstract class unevenTicket extends Ticket {
    // Map with the prices which each person needs to pay to the payer of this ticket
    private final HashMap<Person, Double> pricePerPerson;

    public unevenTicket(Person payer, HashMap<Person, Double> pricePerPerson) {
        super(payer);
        this.pricePerPerson = pricePerPerson;
    }

    public HashMap<Person, Double> getPricePerPerson() {
        return pricePerPerson;
    }

}
