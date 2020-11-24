package ticket;

import person.Person;
import java.util.HashMap;

public class unevenTicket extends Ticket{
    private HashMap<Person, Double> pricePerPerson;

    public unevenTicket(Person person, HashMap<Person, Double> pricePerPerson) {
        super(person);
        this.pricePerPerson = pricePerPerson;
    }

    public HashMap<Person, Double> getPricePerPerson() {
        return pricePerPerson;
    }

}
