package gui.frames;

import database.PersonDB;
import database.TicketDB;
import person.Person;
import priceCalculator.PriceCalculator;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Observable;
import java.util.Observer;

public class mainFrame extends JFrame implements Observer {

    private JPanel container;

    private JButton buttonPerson;
    private JButton buttonTicket;
    private JButton deleteTicket;
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

    private JList<String> paymentList;
    private DefaultListModel<String> paymentModel;
    private JScrollPane paymentScroll;
    private JLabel paymentLabel;

    private Observable observable;

    private PriceCalculator priceCalculator;

    public mainFrame(String title, Observable observable){
        super(title);

        observable.addObserver(this);

        personModel = new DefaultListModel<>();
        personList = new JList<>(personModel);

        ticketModel = new DefaultListModel<>();
        ticketList = new JList<>(ticketModel);

        paymentModel = new DefaultListModel<>();
        paymentList = new JList<>(paymentModel);

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
        deleteTicket = new JButton("Delete Ticket");
        refreshButton = new JButton("Refresh");

        personLabel = new JLabel("All Persons");
        ticketLabel = new JLabel("All Tickets");
        paymentLabel = new JLabel("Prices To Pay");

        // https://stackoverflow.com/questions/3200846/how-to-make-a-scrollable-jlist-to-add-details-got-from-a-joptionpane
        personScroll = new JScrollPane(personList);
        ticketScroll = new JScrollPane(ticketList);
        paymentScroll = new JScrollPane(paymentList);


        this.addObjects(personLabel, container, layout, gbc, 0, 0, 1, 1, GridBagConstraints.CENTER);
        this.addObjects(ticketLabel, container, layout, gbc, 1, 0, 1, 1, GridBagConstraints.CENTER);
        this.addObjects(paymentLabel, container, layout, gbc, 2, 0, 1, 1, GridBagConstraints.CENTER);

        this.addObjects(personScroll, container, layout, gbc, 0, 1, 1, 3, GridBagConstraints.BOTH);
        this.addObjects(ticketScroll, container, layout, gbc, 1, 1, 1, 3, GridBagConstraints.BOTH);
        this.addObjects(paymentScroll, container, layout, gbc, 2, 1, 2, 3, GridBagConstraints.BOTH);

        gbc.anchor = GridBagConstraints.SOUTH; // Buttons will stick to bottom of grid => bottom of window
        this.addObjects(deletePerson, container, layout, gbc, 0, 4, 1, 1, GridBagConstraints.HORIZONTAL);
        this.addObjects(deleteTicket, container, layout, gbc, 1, 4, 1, 1, GridBagConstraints.HORIZONTAL);
        this.addObjects(buttonPerson, container, layout, gbc, 2, 4, 1, 1, GridBagConstraints.HORIZONTAL);
        this.addObjects(buttonTicket, container, layout, gbc, 3, 4, 1, 1, GridBagConstraints.HORIZONTAL);

        gbc.anchor = GridBagConstraints.NORTHEAST;
        this.addObjects(refreshButton, container, layout, gbc, 3, 0, 1, 1, GridBagConstraints.HORIZONTAL);

        //https://stackoverflow.com/questions/21879243/how-to-create-on-click-event-for-buttons-in-swing
        //https://www.w3schools.com/java/java_lambda.asp#:~:text=Lambda%20Expressions%20were%20added%20in,the%20body%20of%20a%20method.
        buttonPerson.addActionListener(e -> switchToAddPerson());

        buttonTicket.addActionListener(e -> switchToAddTicket());

        deletePerson.addActionListener(e -> deletePerson());

        deleteTicket.addActionListener(e -> deleteTicket());

        refreshButton.addActionListener(e -> refresh());

        this.setLocationRelativeTo(null);
    }

    private void refreshPricesToPay(){
        paymentModel.clear();
        priceCalculator.calculatePrices();
        HashMap<Person, HashMap<Person, Double>> map = new HashMap<>(priceCalculator.getPricesToPay());
        if(!ticketModel.isEmpty()) {
            for (Person payer : map.keySet()) {
                if (!map.getOrDefault(payer, null).isEmpty()) {
                    for (Person x : map.get(payer).keySet()) {
                        if(map.get(payer).getOrDefault(x, 0.0) != 0.0){
                            String temp = x + " has to pay " + map.get(payer).get(x) + " to " + payer;
                            paymentModel.addElement(temp);
                        }
                    }
                }
            }
        } else {
            paymentModel.addElement("No tickets");
        }
    }

    private void deleteTicket() {
        if(ticketList.getSelectedValue() == null){
            System.out.println("Nothing selected");
        } else {
            TicketDB.getInstance().removeTicket(ticketList.getSelectedValue());
            ticketModel.removeElement(ticketList.getSelectedValue());
        }
        refresh();
    }

    private void deletePerson() {
        if(personList.getSelectedValue() == null){
            System.out.println("Nothing selected");
        } else {
            ArrayList<String> tickets = PersonDB.getInstance().getHashMap().get(personList.getSelectedValue());
            PersonDB.getInstance().removePerson(personList.getSelectedValue());
            personModel.removeElement(personList.getSelectedValue());
            for(String x: tickets){
                deleteTicketOfPerson(x);
            }
        }
        refresh();
    }

    public void refresh(){
        // TODO initial refresh problems
        refreshPricesToPay();
    }

    private void deleteTicketOfPerson(String name){
        TicketDB.getInstance().removeTicketOnly(name);
        ticketModel.removeElement(name);
    }

    private void switchToAddTicket(){
        addUnevenTicketFrame ticketFrame = new addUnevenTicketFrame("Add ticket", this, personModel, personList.getSelectedValue());
        this.setVisible(false);
        ticketFrame.setVisible(true);

    }

    private void switchToAddPerson(){
        addPersonFrame personFrame = new addPersonFrame("Add person", this);
        this.setVisible(false);
        personFrame.setVisible(true);
    }

    // Function to simplify the process of adding constrains to components
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
