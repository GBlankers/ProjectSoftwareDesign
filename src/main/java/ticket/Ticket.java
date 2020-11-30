package ticket;

import person.Person;

public class Ticket {
    private Person payer;

    public Ticket(Person payer){
        this.payer = payer;
    }

    public Person getPayer() {
        return payer;
    }
}
