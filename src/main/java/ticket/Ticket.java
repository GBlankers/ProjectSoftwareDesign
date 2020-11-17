package ticket;

import person.Person;

public abstract class Ticket {
    private String payer;

    public Ticket(Person person){
        this.payer = person.getName();
    }

    public String getPayer() {
        return payer;
    }
}
