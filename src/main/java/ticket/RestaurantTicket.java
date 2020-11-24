package ticket;

import person.Person;
import java.util.HashMap;

public class RestaurantTicket extends unevenTicket{

    public RestaurantTicket(Person person, HashMap<Person, Double> pricePerPerson){
        super(person, pricePerPerson);
    }

}
