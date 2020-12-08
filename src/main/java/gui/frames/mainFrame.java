package gui.frames;

import database.Database;
import database.PersonDB;
import database.TicketDB;
import gui.mvController;
import person.Person;
import priceCalculator.PriceCalculator;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Observable;
import java.util.Observer;

public class mainFrame extends JFrame implements Observer {

    // Main Panel
    private JPanel container;

    // Buttons
    private JButton buttonPerson;
    private JButton buttonTicket;
    private JButton deleteTicket;
    private JButton deletePerson;

    // List to show persons
    private JList<Person> personList;
    public DefaultListModel<Person> personModel;
    private JScrollPane personScroll;
    private JLabel personLabel;

    // List to show tickets
    private JList<String> ticketList;
    public DefaultListModel<String> ticketModel;
    private JScrollPane ticketScroll;
    private JLabel ticketLabel;

    // List to show prices to pay
    private JList<String> paymentList;
    private DefaultListModel<String> paymentModel;
    private JScrollPane paymentScroll;
    private JLabel paymentLabel;

    private Observable observable;

    private PriceCalculator priceCalculator;

    protected mvController controller;

    public mainFrame(String title, Observable observable){
        super(title);

        observable.addObserver(this);

        controller = new mvController(this);

        // Initialize lists
        personModel = new DefaultListModel<>();
        personList = new JList<>(personModel);

        ticketModel = new DefaultListModel<>();
        ticketList = new JList<>(ticketModel);

        paymentModel = new DefaultListModel<>();
        paymentList = new JList<>(paymentModel);

        priceCalculator = new PriceCalculator();

        initialize();
    }

    // Initialize the contents + layout of the main frame
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

        personLabel = new JLabel("All Persons");
        ticketLabel = new JLabel("All Tickets");
        paymentLabel = new JLabel("Prices To Pay");

        // https://stackoverflow.com/questions/3200846/how-to-make-a-scrollable-jlist-to-add-details-got-from-a-joptionpane
        personScroll = new JScrollPane(personList);
        ticketScroll = new JScrollPane(ticketList);
        paymentScroll = new JScrollPane(paymentList);


        this.addObjects(personLabel, container, layout, gbc, 0, 0, 1, 1, GridBagConstraints.CENTER);
        this.addObjects(ticketLabel, container, layout, gbc, 1, 0, 1, 1, GridBagConstraints.CENTER);
        this.addObjects(paymentLabel, container, layout, gbc, 2, 0, 2, 1, GridBagConstraints.CENTER);

        this.addObjects(personScroll, container, layout, gbc, 0, 1, 1, 3, GridBagConstraints.BOTH);
        this.addObjects(ticketScroll, container, layout, gbc, 1, 1, 1, 3, GridBagConstraints.BOTH);
        this.addObjects(paymentScroll, container, layout, gbc, 2, 1, 2, 3, GridBagConstraints.BOTH);

        gbc.anchor = GridBagConstraints.SOUTH; // Buttons will stick to bottom of grid => bottom of window
        this.addObjects(deletePerson, container, layout, gbc, 0, 4, 1, 1, GridBagConstraints.HORIZONTAL);
        this.addObjects(deleteTicket, container, layout, gbc, 1, 4, 1, 1, GridBagConstraints.HORIZONTAL);
        this.addObjects(buttonPerson, container, layout, gbc, 2, 4, 1, 1, GridBagConstraints.HORIZONTAL);
        this.addObjects(buttonTicket, container, layout, gbc, 3, 4, 1, 1, GridBagConstraints.HORIZONTAL);

        //https://stackoverflow.com/questions/21879243/how-to-create-on-click-event-for-buttons-in-swing
        //https://www.w3schools.com/java/java_lambda.asp#:~:text=Lambda%20Expressions%20were%20added%20in,the%20body%20of%20a%20method.
        buttonPerson.addActionListener(e -> switchToAddPerson());

        buttonTicket.addActionListener(e -> switchToAddTicket());

        deletePerson.addActionListener(e -> controller.deletePerson(personList.getSelectedValue()));

        deleteTicket.addActionListener(e -> controller.deleteTicket(ticketList.getSelectedValue()));

        // Window will pop up in the middle of the screen
        this.setLocationRelativeTo(null);
    }

    // Refresh the contents of the payment panel
    public void refresh(){
        // Delete the previous calculations already displayed
        paymentModel.clear();
        // Re calculate the prices
        priceCalculator.calculatePrices();
        // Get the price mapping
        HashMap<Person, HashMap<Person, Double>> map = new HashMap<>(priceCalculator.getPricesToPay());
        // Check if there are tickets
        if(!ticketModel.isEmpty()) {
            // Generate string: Person x had to pay ... to person Y. Put this in the payment panel
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

    // Switch to the add tickets frames
    private void switchToAddTicket(){
        // Switch frames to the unevenTicketFrame
        addUnevenTicketFrame ticketFrame = new addUnevenTicketFrame("Add ticket", controller, personModel, personList.getSelectedValue());
        // set this frame invisible and show the add ticket frame
        this.setVisible(false);
        ticketFrame.setVisible(true);
    }

    // Switch to the add person frame
    private void switchToAddPerson(){
        addPersonFrame personFrame = new addPersonFrame("Add person", controller);
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

    // Observer pattern
    @Override
    public void update(Observable o, Object arg) {
        if(o instanceof Database){
            if(arg instanceof Person) {
                Person p = (Person) arg;
                personModel.addElement(p);
            } else if(arg instanceof String){
                String s = (String) arg;
                ticketModel.addElement(s);
            }
        refresh();
        }
    }
}
