package ticket.evenTickets;

import person.Person;
import ticket.Ticket;

public abstract class evenTicket extends Ticket {
    private final double totalPrice;

    public evenTicket(Person payer, double totalPrice) {
        super(payer);
        this.totalPrice = totalPrice;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

}
