package factory;

import person.Person;

public interface personFactoryAbstract {
    // person factories need to add persons
    Person addPerson(String name);
}
