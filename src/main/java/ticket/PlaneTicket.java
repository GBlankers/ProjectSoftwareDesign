package ticket;

import person.Person;

public class PlaneTicket extends Ticket{
    private double totalPrice;

    public PlaneTicket(Person person, double totalPrice){
        super(person);
        this.totalPrice = totalPrice;
    }
}
