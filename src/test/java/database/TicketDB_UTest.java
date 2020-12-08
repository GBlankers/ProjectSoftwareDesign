package database;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
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
    public void inDb() {
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
    public void getHashMap() {
    }

    @Test
    public void clear() {
    }

    @Test
    public void getInstance() {
        TicketDB db1 = TicketDB.getInstance();
        TicketDB db2 = TicketDB.getInstance();

        Assert.assertEquals("Tests the singleton pattern", db1.toString(), db2.toString());
    }

    @Test
    public void removeTicketOnly() {
    }

    @Test
    public void removeTicket() {
    }
}
