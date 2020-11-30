package ticket;

import person.Person;

public class evenTicket extends Ticket{
    private double totalPrice;

    public evenTicket(Person payer, double totalPrice) {
        super(payer);
        this.totalPrice = totalPrice;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

}
