package priceCalculator;

import database.PersonDB;
import database.TicketDB;
import factory.personFactory;
import factory.ticketFactory;
import person.Person;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.util.HashMap;


// Run with PowerMock, an extended version of Mockito
@RunWith(PowerMockRunner.class)
// Prepare class RegistrationController for testing by injecting mocks
@PrepareForTest(PriceCalculator.class)
public class priceCalculator_ITest {

    private final PersonDB personDB = PersonDB.getInstance();
    private final TicketDB ticketDB = TicketDB.getInstance();

    private Person person1;
    private Person person2;
    private Person person3;
    private Person person4;

    public priceCalculator_ITest(){

    }

    @Before
    public void initialize(){
        // Make sure db's are empty before every test
        ticketDB.clear();
        personDB.clear();
    }

    @Test
    public void t_EvenTicketCalculation(){
        ticketFactory factT = new ticketFactory();
        personFactory factP = new personFactory();

        person1 = factP.addPerson("P1");
        person2 = factP.addPerson("P2");
        person3 = factP.addPerson("P3");
        person4 = factP.addPerson("P4");

        HashMap<Person, Double> e_debtToPerson1 = new HashMap<Person, Double>(){{
            put(person2, 100.0);
            put(person3, 100.0);
            put(person4, 100.0);
        }};

        HashMap<Person, HashMap<Person, Double>> expected = new HashMap<Person, HashMap<Person, Double>>(){{
            put(person1, e_debtToPerson1);
            put(person2, new HashMap<>());
            put(person3, new HashMap<>());
            put(person4, new HashMap<>());
        }};

        factT.addTicket("plane", "testTicket", person1, 400);

        PriceCalculator calculator = new PriceCalculator();
        calculator.calculatePrices();

        Assert.assertEquals("Testing price calculation for even ticket", expected, calculator.getPricesToPay());

    }

    @Test
    public void t_UnEvenTicketCalculation(){
        ticketFactory factT = new ticketFactory();
        personFactory factP = new personFactory();

        person1 = factP.addPerson("P1");
        person2 = factP.addPerson("P2");
        person3 = factP.addPerson("P3");
        person4 = factP.addPerson("P4");

        HashMap<Person, Double> debtToPerson1 = new HashMap<Person, Double>(){{
            put(person2, 30.0);
            put(person3, 40.0);
            put(person4, 50.0);
        }};

        HashMap<Person, HashMap<Person, Double>> expected = new HashMap<Person, HashMap<Person, Double>>(){{
            put(person1, debtToPerson1);
            put(person2, new HashMap<>());
            put(person3, new HashMap<>());
            put(person4, new HashMap<>());
        }};

        factT.addTicket("restaurant", "testTicket", person1, debtToPerson1);

        PriceCalculator calculator = new PriceCalculator();
        calculator.calculatePrices();

        Assert.assertEquals("Testing price calculation for uneven ticket", expected, calculator.getPricesToPay());
    }

    @Test
    public void t_comboTicketCalculation(){
        ticketFactory factT = new ticketFactory();
        personFactory factP = new personFactory();

        person1 = factP.addPerson("P1");
        person2 = factP.addPerson("P2");
        person3 = factP.addPerson("P3");
        person4 = factP.addPerson("P4");

        HashMap<Person, Double> debtToPerson1 = new HashMap<Person, Double>(){{
            put(person2, 30.0);
            put(person3, 40.0);
            put(person4, 50.0);
        }};

        HashMap<Person, Double> debtToPerson4 = new HashMap<Person, Double>(){{
            put(person1, 60.0);
            put(person2, 120.0);
            put(person3, 80.0);
        }};

        factT.addTicket("restaurant", "testTicket", person1, debtToPerson1);
        factT.addTicket("plane", "test2Ticket", person2, 400);
        factT.addTicket("plane", "test3Ticket", person3, 800);
        factT.addTicket("restaurant", "test4Ticket", person4, debtToPerson4);

        HashMap<Person, Double> e_debtToPerson1 = new HashMap<Person, Double>(){{
            put(person2, 0.0);
            put(person3, 0.0);
            put(person4, 0.0);
        }};

        HashMap<Person, Double> e_debtToPerson2 = new HashMap<Person, Double>(){{
            put(person1, 70.0);
            put(person3, 0.0);
            put(person4, 0.0);
        }};

        HashMap<Person, Double> e_debtToPerson3 = new HashMap<Person, Double>(){{
            put(person1, 160.0);
            put(person2, 100.0);
            put(person4, 120.0);
        }};

        HashMap<Person, Double> e_debtToPerson4 = new HashMap<Person, Double>(){{
            put(person1, 10.0);
            put(person2, 20.0);
            put(person3, 0.0);
        }};

        HashMap<Person, HashMap<Person, Double>> expected = new HashMap<Person, HashMap<Person, Double>>(){{
            put(person1, e_debtToPerson1);
            put(person2, e_debtToPerson2);
            put(person3, e_debtToPerson3);
            put(person4, e_debtToPerson4);
        }};

        PriceCalculator calculator = new PriceCalculator();
        calculator.calculatePrices();

        Assert.assertEquals("Testing price calculation for combo ticket", expected, calculator.getPricesToPay());
    }


}
