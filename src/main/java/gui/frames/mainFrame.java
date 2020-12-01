package gui.frames;

import database.PersonDB;
import person.Person;

import javax.swing.*;
import java.awt.*;
import java.util.Observable;
import java.util.Observer;

public class mainFrame extends JFrame implements Observer {

    private JPanel container;

    private JButton button1;
    private JButton button2;

    private JList<Person> personList;
    private DefaultListModel<Person> personModel;
    private JLabel personLabel;

    private JList<String> ticketList;
    private DefaultListModel<String> ticketModel;
    private JLabel ticketLabel;

    private Observable observable;

    public mainFrame(String title, Observable observable){
        super(title);

        observable.addObserver(this);

        personModel = new DefaultListModel<>();
        personList = new JList<>(personModel);

        ticketModel = new DefaultListModel<>();
        ticketList = new JList<>(ticketModel);

        initialize();
    }

    public void initialize(){
        this.setSize(400, 400);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        container = new JPanel();
        GridBagLayout layout = new GridBagLayout();
        GridBagConstraints gbc = new GridBagConstraints();

        container.setLayout(layout);
        getContentPane().add(container);

        button1 = new JButton("Add Person");
        button2 = new JButton("Add Ticket");

        personLabel = new JLabel("All Persons");
        ticketLabel = new JLabel("All Tickets");

        this.addObjects(button1, container, layout, gbc, 2, 3, 1, 1);
        this.addObjects(button2, container, layout, gbc, 3, 3, 1, 1);
        this.addObjects(personLabel, container, layout, gbc, 0, 0, 1, 1);
        this.addObjects(personList, container, layout, gbc, 0, 1, 1, 1);
        this.addObjects(ticketLabel, container, layout, gbc, 1, 0, 1, 1);
        this.addObjects(ticketList, container, layout, gbc, 1, 1, 1, 1);
    }

    public void addObjects(Component component, Container container, GridBagLayout layout, GridBagConstraints gbc, int gridx, int gridy, int gridwidth, int gridheight){

        gbc.gridx = gridx;
        gbc.gridy = gridy;

        gbc.gridwidth = gridwidth;
        gbc.gridheight = gridheight;

        layout.setConstraints(component, gbc);
        container.add(component);
    }


    @Override
    public void update(Observable o, Object arg) {
        if(o instanceof PersonDB){
            if(arg instanceof Person){
                Person p = (Person) arg;
                personModel.addElement(p);
            } else if(arg instanceof String){
                String s = (String) arg;
                ticketModel.addElement(s);
            }

        }
    }
}
