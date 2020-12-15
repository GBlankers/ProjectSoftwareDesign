package database;

import factory.personFactory;
import factory.ticketFactory;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import person.Person;
import ticket.Ticket;
import ticket.evenTickets.PlaneTicket;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;


// Run with PowerMock, an extended version of Mockito
@RunWith(PowerMockRunner.class)
// Prepare class RegistrationController for testing by injecting mocks
@PrepareForTest(TicketDB.class)
public class TicketDB_ITest {

    public TicketDB_ITest(){}

    @Before
    public void initialize(){}

    @Test
    public void t_removeTicket(){

        TicketDB ticketDB_underTest = TicketDB.getInstance();
        PersonDB personDB_underTest = PersonDB.getInstance();

        personFactory factoryP = new personFactory();
        ticketFactory factoryT = new ticketFactory();

        Person testPerson = factoryP.addPerson("testPerson");
        factoryP.addPerson("test2Person");

        String testName = "testName";
        String test2Name = "test2Name";

        factoryT.addTicket("plane", testName, testPerson, 400);
        factoryT.addTicket("plane", test2Name, testPerson, 700);

        ArrayList<String> expected = new ArrayList<String>() {{
            add(test2Name);
        }};

        Assert.assertTrue("Test for remove_ticket method - Must be True", ticketDB_underTest.inDb(testName));
        ticketDB_underTest.removeTicket(testName);
        Assert.assertFalse("Test for remove_ticket method - Must be False", ticketDB_underTest.inDb(testName));
        Assert.assertEquals("Test for remove_ticket method", expected, personDB_underTest.getTickets(testPerson));
        Assert.assertTrue("Test for remove_ticket method - Must be True", ticketDB_underTest.inDb(test2Name));
    }
}
