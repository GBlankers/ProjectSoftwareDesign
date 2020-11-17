package ticket;

import person.Person;
import java.util.HashMap;

public class RestaurantTicket extends Ticket{
    private HashMap<Person, Double> pricePerPerson;

    public RestaurantTicket(Person person, HashMap<Person, Double> pricePerPerson){
        super(person);
        this.pricePerPerson = pricePerPerson;
    }
}
