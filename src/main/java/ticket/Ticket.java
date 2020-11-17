package ticket;

import person.Person;

public abstract class Ticket {
    private String name;
    private String payer;

    public Ticket(String name, Person person){
        this.name = name;
        this.payer = person.getName();
    }

    public String getName() {
        return name;
    }

    public String getPayer() {
        return payer;
    }

}
