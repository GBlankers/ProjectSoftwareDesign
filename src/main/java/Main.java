import database.PersonDB;
import database.TicketDB;
import factory.personFactory;
import factory.ticketFactory;
import gui.Gui;
import person.Person;

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
        personFactory factP = new personFactory();

        Gui gui = new Gui();

        Person person1 = factP.addPerson("P1");
        Person person2 = factP.addPerson("P2");
        Person person3 = factP.addPerson("P3");
        Person person4 = factP.addPerson("P4");

        TicketDB ticketDB = TicketDB.getInstance();
        PersonDB personDB = PersonDB.getInstance();

        PriceCalculator priceCalculator = new PriceCalculator();

        ticketFactory factT = new ticketFactory();

        factT.addTicket("plane", "planeTicket1", person1, 400.0);
        HashMap<Person, Double> restBill = new HashMap<>();
        restBill.put(person1, 30.0);
        restBill.put(person3, 50.0);
        restBill.put(person4, 70.0);
        factT.addTicket("restaurant", "rest1", person2, restBill);

        HashMap<Person, Double> temp = priceCalculator.calculatePrices();

        printHashMap(temp);

        sleep(5000);

        Person person5 = factP.addPerson("P5");
    }

    public void printHashMap(HashMap<Person, Double> hashMap){
        for(Person x: hashMap.keySet()){
            System.out.println(x + "has to pay $" + hashMap.getOrDefault(x, 0.0));
        }
    }

    public void sleep(int millis){
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
