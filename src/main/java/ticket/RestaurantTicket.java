package ticket;

import person.Person;

import java.util.HashMap;

public class RestaurantTicket extends Ticket{
    private HashMap<Person, Double> pricePerPerson;

    public RestaurantTicket(String name, Person person, HashMap<Person, Double> pricePerPerson){
        super(name, person);
        this.pricePerPerson = pricePerPerson;
    }
}
