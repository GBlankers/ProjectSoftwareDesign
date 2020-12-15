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

    public PersonDB_UTest(){}

    @Before
    public void initialize(){

    }

    @Test
    @SuppressWarnings("unchecked")
    public void t_inDb() throws NoSuchFieldException, IllegalAccessException {
        Field field = Database.class.getDeclaredField("db");
        field.setAccessible(true);

        PersonDB personDB_underTest = PersonDB.getInstance();

        Person testPerson = Mockito.mock(Person.class);
        ArrayList<String> testList = Mockito.mock(ArrayList.class);
        Person test2Person = Mockito.mock(Person.class);

        HashMap<Person, ArrayList<String>> hm = new HashMap<Person, ArrayList<String>>() {{
            put(testPerson, testList);
        }};

        field.set(personDB_underTest, hm);

        Assert.assertTrue("Test for in_db method - Must be True", personDB_underTest.inDb(testPerson));
        Assert.assertFalse("Test for in_db method - Must be false", personDB_underTest.inDb(test2Person));
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
        Assert.assertEquals("Testing getEntry - should return mockObject", returnedList, mockList);
    }

    @Test
    @SuppressWarnings("unchecked")
    public void t_clear() throws NoSuchFieldException, IllegalAccessException {
        Field field = Database.class.getDeclaredField("db");
        field.setAccessible(true);

        PersonDB personDB_underTest = PersonDB.getInstance();

        Person testPerson = Mockito.mock(Person.class);
        ArrayList<String> testList = Mockito.mock(ArrayList.class);

        HashMap<Person, ArrayList<String>> hm = new HashMap<Person, ArrayList<String>>() {{
            put(testPerson, testList);
        }};

        field.set(personDB_underTest, hm);

        Assert.assertFalse("Check if db is cleared - Must be false", ((HashMap<Person, ArrayList<String>>) field.get(personDB_underTest)).isEmpty());
        personDB_underTest.clear();
        Assert.assertTrue("Check if db is cleared - Must be true", ((HashMap<Person, ArrayList<String>>) field.get(personDB_underTest)).isEmpty());
    }

    @Test
    public void t_getInstance() {
        PersonDB db1 = PersonDB.getInstance();
        PersonDB db2 = PersonDB.getInstance();

        Assert.assertEquals("Tests the singleton pattern", db1.toString(), db2.toString());
    }

    @Test
    @SuppressWarnings("unchecked")
    public void t_removePerson() throws NoSuchFieldException, IllegalAccessException {
        Field field = Database.class.getDeclaredField("db");
        field.setAccessible(true);

        PersonDB personDB_underTest = PersonDB.getInstance();

        Person testPerson = Mockito.mock(Person.class);
        // Can't be a mocked object => in remove person the arraylist will be iterated
        ArrayList<String> testList = new ArrayList<>();
        Person test2Person = Mockito.mock(Person.class);
        ArrayList<String> test2List = Mockito.mock(ArrayList.class);

        HashMap<Person, ArrayList<String>> hm = new HashMap<Person, ArrayList<String>>() {{
            put(testPerson, testList);
            put(test2Person, test2List);
        }};

        field.set(personDB_underTest, hm);

        Assert.assertTrue("Test for remove_person method - Must be True", personDB_underTest.inDb(testPerson));
        personDB_underTest.removePerson(testPerson);
        Assert.assertFalse("Test for remove_person method - Must be False", personDB_underTest.inDb(testPerson));
        Assert.assertTrue("Test for remove_person method - Must be True", personDB_underTest.inDb(test2Person));
    }

    @Test
    public void t_addTicket() throws NoSuchFieldException, IllegalAccessException {
        Field field = Database.class.getDeclaredField("db");
        field.setAccessible(true);

        PersonDB personDB_underTest = PersonDB.getInstance();

        Person testPerson = Mockito.mock(Person.class);
        ArrayList<String> testList = new ArrayList<String>(){{
            add("Ticket1");
            add("Ticket2");
        }};

        // = testList + ticket3
        ArrayList<String> expected = new ArrayList<String>(testList){{
            add("Ticket3");
        }};

        HashMap<Person, ArrayList<String>> hm = new HashMap<Person, ArrayList<String>>() {{
            put(testPerson, testList);
        }};

        field.set(personDB_underTest, hm);

        personDB_underTest.addTicket(testPerson, "Ticket3");

        Assert.assertEquals("Check add_ticket method", expected, personDB_underTest.getTickets(testPerson));
    }

    @Test
    public void t_removeTicket() throws NoSuchFieldException, IllegalAccessException {
        Field field = Database.class.getDeclaredField("db");
        field.setAccessible(true);

        PersonDB personDB_underTest = PersonDB.getInstance();

        Person testPerson = Mockito.mock(Person.class);
        ArrayList<String> testList = new ArrayList<String>(){{
            add("Ticket1");
            add("Ticket2");
        }};

        ArrayList<String> expected = new ArrayList<String>(testList){{
            remove("Ticket2");
        }};

        HashMap<Person, ArrayList<String>> hm = new HashMap<Person, ArrayList<String>>() {{
            put(testPerson, testList);
        }};

        field.set(personDB_underTest, hm);

        personDB_underTest.removeTicket(testPerson, "Ticket2");

        Assert.assertEquals("Check add_ticket method", expected, personDB_underTest.getTickets(testPerson));
    }
}
