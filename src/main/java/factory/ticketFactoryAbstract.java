package factory;

import person.Person;

import java.util.HashMap;

public interface ticketFactoryAbstract {
    // One interface to add tickets => method overloading needed
    void addTicket(String type, String ticketName, Person payer, double totalPrice);
    void addTicket(String type, String ticketName, Person payer, HashMap<Person, Double> pricePerPerson);
}
