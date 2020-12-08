package gui.frames;

import gui.mvController;
import person.Person;

import javax.swing.*;
import java.awt.*;

public class addEvenTicketFrame extends JFrame{

    public JTextField ticketName;

    public JTextField ticketType;

    public JTextField totalPrice;

    // Dropdown menu to chose un/even ticket and payer
    public JComboBox<Person> payerComboBox;
    private JComboBox<String> un_evenCombobox;

    // Extra variables for the dropdown menu
    private final String[] T = {"even ticket", "uneven ticket"};
    private final Person[] persons;
    private final DefaultListModel<Person> personsModel;

    // Person which is selected in the main frame
    private final Person selectedPerson;

    private final mvController controller;

    public addEvenTicketFrame(String title, mvController controller, DefaultListModel<Person> persons, Person selectedPerson){
        super(title);

        // Initialize variables
        this.controller = controller;
        this.personsModel = persons;
        this.selectedPerson = selectedPerson;

        // Initialize array for dropdown menu
        this.persons = new Person[persons.getSize()];
        for(int i=0; i< persons.getSize(); i++){
            this.persons[i] = persons.get(i);
        }
        initialize();
    }

    // Initialize the contents + layout of the frame
    public void initialize(){
        this.setSize(500, 300);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Main content panel
        JPanel container = new JPanel();
        GridBagLayout layout = new GridBagLayout();
        GridBagConstraints gbc = new GridBagConstraints();

        container.setLayout(layout);
        getContentPane().add(container);

        // Name input
        JLabel nameLabel = new JLabel("Name of the ticket:");
        this.ticketName = new JTextField(30);
        // Ticket type input
        JLabel typeLabel = new JLabel("Type of the ticket:");
        this.ticketType = new JTextField(30);
        // Price input
        JLabel priceLabel = new JLabel("Total Price: ");
        this.totalPrice = new JTextField(20);
        // Add ticket button
        JButton okButton = new JButton("Add");


        un_evenCombobox = new JComboBox<>(T);
        payerComboBox = new JComboBox<>(this.persons);
        payerComboBox.setSelectedItem(selectedPerson);

        this.addObjects(un_evenCombobox, container, layout, gbc, 0, 0, 1, 1);
        this.addObjects(new JLabel("Payer: "), container, layout, gbc, 2, 0, 1, 1);
        this.addObjects(payerComboBox, container, layout, gbc, 3, 0, 1, 1);
        this.addObjects(nameLabel, container, layout, gbc, 0, 1, 4, 1);
        this.addObjects(ticketName, container, layout, gbc, 0, 2, 4, 1);
        this.addObjects(typeLabel, container, layout, gbc, 0, 3, 4, 1);
        this.addObjects(ticketType, container, layout, gbc, 0, 4, 4, 1);

        this.addObjects(priceLabel, container, layout, gbc, 0, 5, 1, 1);
        this.addObjects(totalPrice, container, layout, gbc, 1, 5, 3, 1);

        gbc.anchor = GridBagConstraints.NORTHEAST;
        this.addObjects(okButton, container, layout, gbc, 3, 6, 1, 1);

        okButton.addActionListener(e -> controller.addTicket(this));

        un_evenCombobox.addActionListener(e -> changeTicketType());

        totalPrice.addActionListener(e -> controller.addTicket(this));

        this.setLocationRelativeTo(null);
    }

    // Switch to uneven ticket frame if selected in dropdown menu
    private void changeTicketType(){
        String type = (String) un_evenCombobox.getSelectedItem();
        // Check empty string
        if(type != null){
            if(type.equals("uneven ticket")){
                addUnevenTicketFrame unevenTicketFrame = new addUnevenTicketFrame("Add Ticket",
                        controller, personsModel, (Person) this.payerComboBox.getSelectedItem());
                this.setVisible(false);
                unevenTicketFrame.setVisible(true);
            }
        }
    }

    // Function to simplify the process of adding constrains to components
    public void addObjects(Component component, Container container, GridBagLayout layout, GridBagConstraints gbc,
                           int gridX, int gridY, int gridWidth, int gridHeight){
        // https://stackoverflow.com/questions/30656473/how-to-use-gridbaglayout
        gbc.gridx = gridX;
        gbc.gridy = gridY;

        gbc.gridwidth = gridWidth;
        gbc.gridheight = gridHeight;

        layout.setConstraints(component, gbc);
        container.add(component);
    }

}
