package database;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import ticket.Ticket;

import java.lang.reflect.Field;
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
    public void t_inDb() throws NoSuchFieldException, IllegalAccessException {
        Field field = Database.class.getDeclaredField("db");
        field.setAccessible(true);

        TicketDB ticketDB_underTest = TicketDB.getInstance();

        String testName = "testTicket";
        Ticket testTicket = Mockito.mock(Ticket.class);
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
    public void t_addToDb() throws NoSuchFieldException, IllegalAccessException {
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
    public void t_getTickets() throws NoSuchFieldException, IllegalAccessException {
        Field field = Database.class.getDeclaredField("db");
        field.setAccessible(true);

        TicketDB ticketDB_underTest = TicketDB.getInstance();
        HashMap<String, Ticket> mock_db = new HashMap<>();
        field.set(ticketDB_underTest, mock_db);

        String mockString = "mockString";
        Ticket mockTicket = Mockito.mock(Ticket.class);
        mock_db.put(mockString, mockTicket);

        Assert.assertEquals("Testing getEntry - should return mockObject", mockTicket, ticketDB_underTest.getTickets(mockString));
    }

    @Test
    public void t_size() throws NoSuchFieldException, IllegalAccessException {
        Field field = Database.class.getDeclaredField("db");
        field.setAccessible(true);

        TicketDB ticketDB_underTest = TicketDB.getInstance();
        ticketDB_underTest.clear();

        Assert.assertEquals("Testing size- Must be 0", 0, ticketDB_underTest.size());

        String testName = "test1";
        Ticket testTicket = Mockito.mock(Ticket.class);

        HashMap<String, Ticket> hm = new HashMap<String, Ticket>() {{
            put(testName, testTicket);
        }};

        field.set(ticketDB_underTest, hm);

        Assert.assertEquals("Testing size- Must be 1", 1, ticketDB_underTest.size());
    }

    @Test
    @SuppressWarnings("unchecked")
    public void t_clear() throws NoSuchFieldException, IllegalAccessException {
        Field field = Database.class.getDeclaredField("db");
        field.setAccessible(true);

        TicketDB ticketDB_underTest = TicketDB.getInstance();

        String testName = "testName";
        Ticket testTicket = Mockito.mock(Ticket.class);

        HashMap<String, Ticket> hm = new HashMap<String, Ticket>() {{
            put(testName, testTicket);
        }};

        field.set(ticketDB_underTest, hm);

        Assert.assertFalse("Check if db is cleared - Must be false", ((HashMap<String, Ticket>) field.get(ticketDB_underTest)).isEmpty());
        ticketDB_underTest.clear();
        Assert.assertTrue("Check if db is cleared - Must be true", ((HashMap<String, Ticket>) field.get(ticketDB_underTest)).isEmpty());
    }

    @Test
    public void t_getInstance() {
        TicketDB db1 = TicketDB.getInstance();
        TicketDB db2 = TicketDB.getInstance();

        Assert.assertEquals("Tests the singleton pattern", db1.toString(), db2.toString());
    }

    @Test
    public void t_removeTicketOnly() throws NoSuchFieldException, IllegalAccessException {
        Field field = Database.class.getDeclaredField("db");
        field.setAccessible(true);

        TicketDB ticketDB_underTest = TicketDB.getInstance();
        String testName = "testName";
        Ticket testTicket = Mockito.mock(Ticket.class);
        String test2Name = "test2Name";
        Ticket test2Ticket = Mockito.mock(Ticket.class);


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

}
