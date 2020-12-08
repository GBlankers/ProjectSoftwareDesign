package database;

import factory.personFactory;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
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
public class TicketDB_UTest {

    public TicketDB_UTest(){}

    @Before
    public void initialize(){}

    @Test
    public void inDb() throws NoSuchFieldException, IllegalAccessException {
        Field field = Database.class.getDeclaredField("db");
        field.setAccessible(true);

        TicketDB ticketDB_underTest = TicketDB.getInstance();

        String testName = "testTicket";
        Ticket testTicket = new PlaneTicket(new Person("testPerson"), 400);
        String test2Name = "test2Ticket";

        HashMap<String, Ticket> hm = new HashMap<String, Ticket>() {{
            put(testName, testTicket);
        }};

        field.set(ticketDB_underTest, hm);

        Assert.assertTrue("Test for in_db method - Must be True", ticketDB_underTest.inDb(testName));
        Assert.assertFalse("Test for in_db method - Must be false", ticketDB_underTest.inDb(test2Name));

    }

    @Test
    @SuppressWarnings("unchecked")
    public void addToDb() throws NoSuchFieldException, IllegalAccessException {
        Field field = Database.class.getDeclaredField("db");
        field.setAccessible(true);

        TicketDB ticketDB_underTest = TicketDB.getInstance();
        HashMap<String, Ticket> mock_db = (HashMap<String, Ticket>) Mockito.mock(HashMap.class);
        field.set(ticketDB_underTest, mock_db);

        // String is a final class => Cannot be mocked
        String mockName = "mockString";
        Ticket mockTicket = Mockito.mock(Ticket.class);

        ticketDB_underTest.addToDb(mockName, mockTicket);
        Mockito.verify(mock_db, Mockito.times(1)).put(mockName, mockTicket);
    }

    @Test
    public void getTickets() throws NoSuchFieldException, IllegalAccessException {
        Field field = Database.class.getDeclaredField("db");
        field.setAccessible(true);

        TicketDB ticketDB_underTest = TicketDB.getInstance();
        HashMap<String, Ticket> mock_db = new HashMap<>();
        field.set(ticketDB_underTest, mock_db);

        String mockString = "mockString";
        Ticket mockTicket = Mockito.mock(Ticket.class);
        mock_db.put(mockString, mockTicket);

        Ticket returnedTicket = ticketDB_underTest.getTickets(mockString);
        Assert.assertEquals("Testing getEntry - should return mockObject", mockTicket, returnedTicket);
    }

    @Test
    @SuppressWarnings("unchecked")
    public void clear() throws NoSuchFieldException, IllegalAccessException {
        Field field = Database.class.getDeclaredField("db");
        field.setAccessible(true);

        TicketDB ticketDB_underTest = TicketDB.getInstance();

        String testName = "testName";
        Ticket testTicket = new PlaneTicket(new Person("testPerson"), 400);

        HashMap<String, Ticket> hm = new HashMap<String, Ticket>() {{
            put(testName, testTicket);
        }};

        field.set(ticketDB_underTest, hm);

        Assert.assertFalse("Check if db is cleared - Must be false", ((HashMap<String, Ticket>) field.get(ticketDB_underTest)).isEmpty());
        ticketDB_underTest.clear();
        Assert.assertTrue("Check if db is cleared - Must be true", ((HashMap<String, Ticket>) field.get(ticketDB_underTest)).isEmpty());
    }

    @Test
    public void getInstance() {
        TicketDB db1 = TicketDB.getInstance();
        TicketDB db2 = TicketDB.getInstance();

        Assert.assertEquals("Tests the singleton pattern", db1.toString(), db2.toString());
    }

    @Test
    public void removeTicketOnly() throws NoSuchFieldException, IllegalAccessException {
        Field field = Database.class.getDeclaredField("db");
        field.setAccessible(true);

        TicketDB ticketDB_underTest = TicketDB.getInstance();
        String testName = "testName";
        Ticket testTicket = new PlaneTicket(new Person("testPerson"), 400);
        String test2Name = "test2Name";
        Ticket test2Ticket = new PlaneTicket(new Person("test2Person"), 700);


        HashMap<String, Ticket> hm = new HashMap<String, Ticket>() {{
            put(testName, testTicket);
            put(test2Name, test2Ticket);
        }};

        field.set(ticketDB_underTest, hm);

        Assert.assertTrue("Test for remove_ticket_only method - Must be True", ticketDB_underTest.inDb(testName));
        ticketDB_underTest.removeTicketOnly(testName);
        Assert.assertFalse("Test for remove_ticket_only method - Must be False", ticketDB_underTest.inDb(testName));
        Assert.assertTrue("Test for remove_ticket_only method - Must be True", ticketDB_underTest.inDb(test2Name));
    }

    @Test
    public void removeTicket() throws NoSuchFieldException, IllegalAccessException {
        Field field = Database.class.getDeclaredField("db");
        field.setAccessible(true);

        TicketDB ticketDB_underTest = TicketDB.getInstance();
        PersonDB personDB_underTest = PersonDB.getInstance();

        personFactory factory = new personFactory();

        Person testPerson = factory.addPerson("testPerson");
        factory.addPerson("test2Person");

        String testName = "testName";
        Ticket testTicket = new PlaneTicket(testPerson, 400);
        String test2Name = "test2Name";
        Ticket test2Ticket = new PlaneTicket(testPerson, 700);

        personDB_underTest.addTicket(testPerson, testName);
        personDB_underTest.addTicket(testPerson, test2Name);

        HashMap<String, Ticket> hm = new HashMap<String, Ticket>() {{
            put(testName, testTicket);
            put(test2Name, test2Ticket);
        }};

        ArrayList<String> expected = new ArrayList<String>(){{
            add(test2Name);
        }};

        field.set(ticketDB_underTest, hm);

        Assert.assertTrue("Test for remove_ticket method - Must be True", ticketDB_underTest.inDb(testName));
        ticketDB_underTest.removeTicket(testName);
        Assert.assertFalse("Test for remove_ticket method - Must be False", ticketDB_underTest.inDb(testName));
        Assert.assertEquals("Test for remove_ticket method", expected, personDB_underTest.getTickets(testPerson));
        Assert.assertTrue("Test for remove_ticket method - Must be True", ticketDB_underTest.inDb(test2Name));
    }
}
