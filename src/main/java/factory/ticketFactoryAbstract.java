package factory;

import person.Person;

import java.util.HashMap;

public interface ticketFactoryAbstract {
    void addTicket(String type, String ticketName, Person payer, double totalPrice);
    void addTicket(String type, String ticketName, Person payer, HashMap<Person, Double> pricePerPerson);
}
