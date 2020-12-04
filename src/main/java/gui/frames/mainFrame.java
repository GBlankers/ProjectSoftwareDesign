package gui.frames;

import database.PersonDB;
import database.TicketDB;
import person.Person;
import priceCalculator.PriceCalculator;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

public class mainFrame extends JFrame implements Observer {

    private JPanel container;

    private JButton buttonPerson;
    private JButton buttonTicket;
    private JButton deletePerson;
    private JButton refreshButton;

    private JList<Person> personList;
    private DefaultListModel<Person> personModel;
    private JScrollPane personScroll;
    private JLabel personLabel;

    private JList<String> ticketList;
    private DefaultListModel<String> ticketModel;
    private JScrollPane ticketScroll;
    private JLabel ticketLabel;

    private Observable observable;

    private PriceCalculator priceCalculator;

    public mainFrame(String title, Observable observable){
        super(title);

        observable.addObserver(this);

        personModel = new DefaultListModel<>();
        personList = new JList<>(personModel);

        ticketModel = new DefaultListModel<>();
        ticketList = new JList<>(ticketModel);

        priceCalculator = new PriceCalculator();

        initialize();
    }

    public void initialize(){
        this.setSize(600, 400);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        container = new JPanel();
        GridBagLayout layout = new GridBagLayout();
        GridBagConstraints gbc = new GridBagConstraints();

        container.setLayout(layout);
        getContentPane().add(container);

        buttonPerson = new JButton("Add Person");
        buttonTicket = new JButton("Add Ticket");
        deletePerson = new JButton("Delete Person");
        refreshButton = new JButton("Refresh");

        personLabel = new JLabel("All Persons");
        ticketLabel = new JLabel("All Tickets");

        // https://stackoverflow.com/questions/3200846/how-to-make-a-scrollable-jlist-to-add-details-got-from-a-joptionpane
        personScroll = new JScrollPane(personList);
        ticketScroll = new JScrollPane(ticketList);


        this.addObjects(personLabel, container, layout, gbc, 0, 0, 1, 1, GridBagConstraints.CENTER);
        this.addObjects(ticketLabel, container, layout, gbc, 1, 0, 1, 1, GridBagConstraints.CENTER);

        this.addObjects(personScroll, container, layout, gbc, 0, 1, 1, 3, GridBagConstraints.BOTH);
        this.addObjects(ticketScroll, container, layout, gbc, 1, 1, 1, 3, GridBagConstraints.BOTH);

        gbc.anchor = GridBagConstraints.SOUTH; // Buttons will stick to bottom of window
        this.addObjects(buttonPerson, container, layout, gbc, 2, 4, 1, 1, GridBagConstraints.HORIZONTAL);
        this.addObjects(buttonTicket, container, layout, gbc, 3, 4, 1, 1, GridBagConstraints.HORIZONTAL);
        this.addObjects(deletePerson, container, layout, gbc, 0, 4, 1, 1, GridBagConstraints.HORIZONTAL);

        gbc.anchor = GridBagConstraints.NORTHEAST;
        this.addObjects(refreshButton, container, layout, gbc, 3, 0, 1, 1, GridBagConstraints.HORIZONTAL);

        //https://stackoverflow.com/questions/21879243/how-to-create-on-click-event-for-buttons-in-swing
        //https://www.w3schools.com/java/java_lambda.asp#:~:text=Lambda%20Expressions%20were%20added%20in,the%20body%20of%20a%20method.
        buttonPerson.addActionListener(e -> switchToAddPerson());

        buttonTicket.addActionListener(e -> switchToAddTicket());

        deletePerson.addActionListener(e -> deletePerson());

        refreshButton.addActionListener(e -> refresh());

        this.setLocationRelativeTo(null);
    }

    private void refresh(){
        System.out.println();
        priceCalculator.calculatePrices();
        priceCalculator.printMapping();
    }

    private void deletePerson() {
        if(personList.getSelectedValue() == null){
            System.out.println("Nothing selected");
        } else {
            ArrayList<String> tickets = PersonDB.getInstance().getHashMap().get(personList.getSelectedValue());
            PersonDB.getInstance().removePerson(personList.getSelectedValue());
            personModel.removeElement(personList.getSelectedValue());
            for(String x: tickets){
                deleteTicket1(x);
            }
        }
    }

    private void deleteTicket1(String name){
        TicketDB.getInstance().removeTicketOnly(name);
        ticketModel.removeElement(name);
    }

    private void switchToAddTicket(){
        addTicketFrame ticketFrame = new addTicketFrame("Add ticket", this);
        this.setVisible(false);
        ticketFrame.setVisible(true);
    }

    private void switchToAddPerson(){
        addPersonFrame personFrame = new addPersonFrame("Add person", this);
        this.setVisible(false);
        personFrame.setVisible(true);
    }

    public void addObjects(Component component, Container container, GridBagLayout layout, GridBagConstraints gbc,
                           int gridx, int gridy, int gridwidth, int gridheight, int fill){
        // https://stackoverflow.com/questions/30656473/how-to-use-gridbaglayout
        // https://stackoverflow.com/questions/24723998/can-components-of-a-gridbaglayout-fill-parent-frame-upon-resize
        gbc.gridx = gridx;
        gbc.gridy = gridy;

        gbc.weightx = 1.0;
        gbc.weighty = 1.0;

        gbc.gridwidth = gridwidth;
        gbc.gridheight = gridheight;

        gbc.fill = fill;

        layout.setConstraints(component, gbc);
        container.add(component);
    }


    @Override
    public void update(Observable o, Object arg) {
        if(o instanceof PersonDB){
            if(arg instanceof Person) {
                Person p = (Person) arg;
                personModel.addElement(p);
            } else if(arg instanceof String){
                String s = (String) arg;
                ticketModel.addElement(s);
            }
        }
    }
}
