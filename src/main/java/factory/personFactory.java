package factory;

import database.PersonDB;
import person.Person;

import java.util.ArrayList;

public class personFactory implements personFactoryAbstract{
    private PersonDB personDB;

    public personFactory(){
        personDB = PersonDB.getInstance();
    }

    @Override
    public Person addPerson(String name) {
        Person temp = new Person(name);
        personDB.addToDb(temp, new ArrayList<>());
        return temp;
    }
}
