package ticket;

import person.Person;
import java.util.HashMap;

public class RestaurantTicket extends unevenTicket{

    public RestaurantTicket(Person payer, HashMap<Person, Double> pricePerPerson){
        super(payer, pricePerPerson);
    }

}
