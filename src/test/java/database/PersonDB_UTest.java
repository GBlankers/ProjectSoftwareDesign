package database;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import person.Person;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;

// Run with PowerMock, an extended version of Mockito
@RunWith(PowerMockRunner.class)
// Prepare class RegistrationController for testing by injecting mocks
@PrepareForTest(PersonDB.class)
public class PersonDB_UTest {

    public PersonDB_UTest(){

    }

    @Before
    public void initialize(){

    }


    @Test
    public void t_inDb() {
    }

    @Test
    @SuppressWarnings("unchecked")
    public void t_addToDb() throws NoSuchFieldException, IllegalAccessException {
        Field field = Database.class.getDeclaredField("db");
        field.setAccessible(true);

        PersonDB personDB_underTest = PersonDB.getInstance();
        HashMap<Person, ArrayList<String>> mock_db = (HashMap<Person, ArrayList<String>>) Mockito.mock(HashMap.class);
        field.set(personDB_underTest, mock_db);

        // String is a final class => Cannot be mocked
        Person mockPerson = Mockito.mock(Person.class);
        ArrayList<String> mockList = Mockito.mock(ArrayList.class);

        personDB_underTest.addToDb(mockPerson, mockList);
        Mockito.verify(mock_db, Mockito.times(1)).put(mockPerson, mockList);
    }

    @Test
    @SuppressWarnings("unchecked")
    public void t_getTickets() throws NoSuchFieldException, IllegalAccessException {
        Field field = Database.class.getDeclaredField("db");
        field.setAccessible(true);

        PersonDB personDB_underTest = PersonDB.getInstance();
        HashMap<Person, ArrayList<String>> mock_db = new HashMap<>();
        field.set(personDB_underTest, mock_db);

        Person mockPerson = Mockito.mock(Person.class);
        ArrayList<String> mockList = Mockito.mock(ArrayList.class);
        mock_db.put(mockPerson, mockList);

        ArrayList<String> returnedList = personDB_underTest.getTickets(mockPerson);
        Assert.assertEquals("Testing getEntry - should return mockObject", mockList, returnedList);
    }

    @Test
    public void t_getHashMap() {
    }

    @Test
    public void t_clear() {
    }

    @Test
    public void t_getInstance() {
        PersonDB db1 = PersonDB.getInstance();
        PersonDB db2 = PersonDB.getInstance();

        Assert.assertEquals("Tests the singleton pattern", db1.toString(), db2.toString());
    }

    @Test
    public void t_removePerson() {
    }

    @Test
    public void t_addTicket() {
    }

    @Test
    public void t_removeTicket() {
    }
}
