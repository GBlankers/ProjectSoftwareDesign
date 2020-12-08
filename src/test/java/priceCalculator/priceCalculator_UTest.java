package priceCalculator;

import database.PersonDB;
import database.TicketDB;
import factory.personFactory;
import factory.ticketFactory;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import person.Person;

import java.lang.reflect.Field;
import java.util.HashMap;

// Run with PowerMock, an extended version of Mockito
@RunWith(PowerMockRunner.class)
// Prepare class RegistrationController for testing by injecting mocks
@PrepareForTest(PriceCalculator.class)
public class priceCalculator_UTest {

    private PersonDB personDB;
    private TicketDB ticketDB;

    private Person person1;
    private Person person2;
    private Person person3;
    private Person person4;

    public priceCalculator_UTest(){

    }

    @Before
    public void initialize(){
        personDB = PersonDB.getInstance();
        ticketDB = TicketDB.getInstance();
    }

    @Test
    public void t_EvenTicketCalculation(){
        ticketFactory factT = new ticketFactory();

        ticketDB.clear();
        personDB.clear();

        personFactory factP = new personFactory();

        person1 = factP.addPerson("P1");
        person2 = factP.addPerson("P2");
        person3 = factP.addPerson("P3");
        person4 = factP.addPerson("P4");

        HashMap<Person, Double> debtToPerson1 = new HashMap<Person, Double>(){{
            put(person2, 100.0);
            put(person3, 100.0);
            put(person4, 100.0);
        }};

        HashMap<Person, HashMap<Person, Double>> expected = new HashMap<Person, HashMap<Person, Double>>(){{
            put(person1, debtToPerson1);
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

        ticketDB.clear();
        personDB.clear();

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
    public void t_SimplifyMapping() throws NoSuchFieldException, IllegalAccessException {
        ticketDB.clear();
        personDB.clear();

        personFactory factP = new personFactory();

        person1 = factP.addPerson("P1");
        person2 = factP.addPerson("P2");
        person3 = factP.addPerson("P3");
        person4 = factP.addPerson("P4");

        Field field = PriceCalculator.class.getDeclaredField("pricesToPay");
        field.setAccessible(true);

        PriceCalculator calculator = new PriceCalculator();

        HashMap<Person, Double> debtToPerson1 = new HashMap<Person, Double>(){{
            put(person2, 30.0);
            put(person3, 40.0);
            put(person4, 50.0);
        }};

        HashMap<Person, Double> debtToPerson2 = new HashMap<Person, Double>(){{
            put(person1, 30.0);
            put(person3, 70.0);
            put(person4, 90.0);
        }};

        HashMap<Person, Double> debtToPerson3 = new HashMap<Person, Double>(){{
            put(person1, 50.0);
            put(person2, 30.0);
            put(person4, 20.0);
        }};

        HashMap<Person, Double> debtToPerson4 = new HashMap<Person, Double>(){{
            put(person1, 40.0);
            put(person2, 10.0);
            put(person3, 0.0);
        }};

        HashMap<Person, HashMap<Person, Double>> priceMap = new HashMap<Person, HashMap<Person, Double>>(){{
            put(person1, debtToPerson1);
            put(person2, debtToPerson2);
            put(person3, debtToPerson3);
            put(person4, debtToPerson4);
        }};

        field.set(calculator, priceMap);

        calculator.simplifyMapping();

        HashMap<Person, Double> e_debtToPerson1 = new HashMap<Person, Double>(){{
            put(person2, 0.0);
            put(person3, 0.0);
            put(person4, 10.0);
        }};

        HashMap<Person, Double> e_debtToPerson2 = new HashMap<Person, Double>(){{
            put(person1, 0.0);
            put(person3, 40.0);
            put(person4, 80.0);
        }};

        HashMap<Person, Double> e_debtToPerson3 = new HashMap<Person, Double>(){{
            put(person1, 10.0);
            put(person2, 0.0);
            put(person4, 20.0);
        }};

        HashMap<Person, Double> e_debtToPerson4 = new HashMap<Person, Double>(){{
            put(person1, 0.0);
            put(person2, 0.0);
            put(person3, 0.0);
        }};

        HashMap<Person, HashMap<Person, Double>> expected = new HashMap<Person, HashMap<Person, Double>>(){{
            put(person1, e_debtToPerson1);
            put(person2, e_debtToPerson2);
            put(person3, e_debtToPerson3);
            put(person4, e_debtToPerson4);
        }};

        Assert.assertEquals("Testing price simplification", expected, calculator.getPricesToPay());
    }

}
