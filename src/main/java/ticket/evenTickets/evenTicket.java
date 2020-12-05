package ticket.evenTickets;

import person.Person;
import ticket.Ticket;

public abstract class evenTicket extends Ticket {
    // total price of the ticket => /#persons = price per person
    private final double totalPrice;

    public evenTicket(Person payer, double totalPrice) {
        super(payer);
        this.totalPrice = totalPrice;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

}
