import database.Database;
import database.PersonDB;
import database.TicketDB;
import factory.ticketFactory;
import person.Person;
import ticket.PlaneTicket;
import ticket.RestaurantTicket;
import ticket.Ticket;

import java.util.HashMap;

public class Main {

    public static void main(String[] args)
    {
        Main main = new Main();
        main.run();
    }

    public Main()
    {

    }

    public void run(){
        Person person1 = new Person("P1");
        Person person2 = new Person("P2");
        Person person3 = new Person("P3");
        Person person4 = new Person("P4");

        TicketDB ticketDB = TicketDB.getInstance();
        PersonDB personDB = PersonDB.getInstance();

        personDB.addPerson(person1);
        personDB.addPerson(person2);
        personDB.addPerson(person3);
        personDB.addPerson(person4);

        PriceCalculator priceCalculator = new PriceCalculator();

        ticketFactory fact = new ticketFactory();

        fact.addTicket("plane", "planeTicket1", person1, 400.0);
        HashMap<Person, Double> restBill = new HashMap<>();
        restBill.put(person1, 30.0);
        restBill.put(person3, 50.0);
        restBill.put(person4, 70.0);
        fact.addTicket("restaurant", "rest1", person2, restBill);

        HashMap<Person, Double> temp = priceCalculator.calculatePrices();

        printHashMap(temp);
    }

    public void printHashMap(HashMap<Person, Double> hashMap){
        for(Person x: hashMap.keySet()){
            System.out.println(x + "has to pay $" + hashMap.getOrDefault(x, 0.0));
        }
    }
}
