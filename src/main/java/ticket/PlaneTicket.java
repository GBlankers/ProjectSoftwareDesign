package ticket;

import person.Person;

public class PlaneTicket extends Ticket{
    private double totalPrice;

    public PlaneTicket(String name, Person person, double totalPrice){
        super(name, person);
        this.totalPrice = totalPrice;
    }
}
