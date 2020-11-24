package gui.panels;

import database.Database;
import database.PersonDB;
import person.Person;

import javax.swing.*;
import java.util.Observable;
import java.util.Observer;

public class PersonPanel extends JPanel implements Observer {
    private JList<Person> entryJList;
    private DefaultListModel<Person> entryListModel;
    private Observable observable;

    public PersonPanel(Observable observable){
        JLabel label = new JLabel("All Persons");

        this.observable = observable;
        observable.addObserver(this);

        entryListModel = new DefaultListModel<>();
        entryJList = new JList<>(entryListModel);

        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        this.add(label);
        this.add(entryJList);
    }

    @Override
    public void update(Observable o, Object arg) {
        if(o instanceof PersonDB){
            Person p = (Person) arg;
            entryListModel.addElement(p);
        }
    }
}
