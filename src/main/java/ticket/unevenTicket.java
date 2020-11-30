package ticket;

import person.Person;
import java.util.HashMap;

public class unevenTicket extends Ticket{
    private HashMap<Person, Double> pricePerPerson;

    public unevenTicket(Person payer, HashMap<Person, Double> pricePerPerson) {
        super(payer);
        this.pricePerPerson = pricePerPerson;
    }

    public HashMap<Person, Double> getPricePerPerson() {
        return pricePerPerson;
    }

}
