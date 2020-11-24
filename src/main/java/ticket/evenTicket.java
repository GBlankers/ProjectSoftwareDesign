package ticket;

import person.Person;

public class evenTicket extends Ticket{
    private double totalPrice;

    public evenTicket(Person person, double totalPrice) {
        super(person);
        this.totalPrice = totalPrice;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

}
