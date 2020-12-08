import factory.personFactory;
import factory.ticketFactory;
import gui.guiController;
import person.Person;
import priceCalculator.PriceCalculator;

import java.util.HashMap;

// TODO abstract factory
// TODO extra design pattern
// TODO Unit + integrate test
// TODO modelio

public class Main {

    public static void main(String[] args)
    {
        Main main = new Main();
    }

    public Main()
    {
        run();
    }

    public void run(){
        personFactory factP = new personFactory();
        guiController guiController = new guiController();

        Person person1 = factP.addPerson("P1");
        Person person2 = factP.addPerson("P2");
        Person person3 = factP.addPerson("P3");
        Person person4 = factP.addPerson("P4");

        PriceCalculator priceCalculator = new PriceCalculator();

        ticketFactory factT = new ticketFactory();

        HashMap<Person, Double> restBill = new HashMap<>();
        restBill.put(person2, 30.0);
        restBill.put(person3, 50.0);
        restBill.put(person4, 70.0);
        factT.addTicket("restaurant", "rest1", person1, restBill);
        factT.addTicket("plane", "planeTicket1", person2, 400.0);

        Person person5 = factP.addPerson("P5");

    }


    public void sleep(int millis){
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
