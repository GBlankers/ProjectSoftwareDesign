package ticket;

import person.Person;

public abstract class Ticket {
    private final Person payer;

    public Ticket(Person payer){
        this.payer = payer;
    }

    public Person getPayer() {
        return payer;
    }
}
