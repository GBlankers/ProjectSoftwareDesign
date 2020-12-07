package gui.frames;

import factory.ticketFactory;
import person.Person;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;

public class addUnevenTicketFrame extends JFrame{
    // Main panel
    private JPanel container;

    // Add ticket button
    private JButton okButton;

    // Ticket name input
    private JLabel nameLabel;
    private JTextField ticketName;

    // Ticket type input
    private JLabel typeLabel;
    private JTextField ticketType;

    // Dropdown menu to chose un/even ticket and payer
    private JComboBox<Person> payerComboBox;
    private JComboBox<String> un_evenCombobox;

    // Extra variables for the dropdown menu
    private final String[] T = {"uneven ticket", "even ticket"};
    private Person[] persons;
    private DefaultListModel<Person> personsModel;

    // Person which is selected in the main frame
    private Person selectedPerson;

    // List to maintain all the dynamically added text fields for each person
    private ArrayList<JTextField> textFields;

    // Remember the parent => switch frames back
    private mainFrame parent;



    public addUnevenTicketFrame(String title, mainFrame parent, DefaultListModel<Person> persons, Person selectedPerson){
        super(title);

        // initialize variables
        this.parent = parent;
        this.personsModel = persons;
        this.selectedPerson = selectedPerson;

        // initialize array for dropdown menu
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

        container = new JPanel();
        GridBagLayout layout = new GridBagLayout();
        GridBagConstraints gbc = new GridBagConstraints();

        container.setLayout(layout);
        getContentPane().add(container);

        nameLabel = new JLabel("Name of the ticket:");
        ticketName = new JTextField(30);
        typeLabel = new JLabel("Type of the ticket:");
        ticketType = new JTextField(30);
        okButton = new JButton("Add");


        un_evenCombobox = new JComboBox<>(T);
        payerComboBox = new JComboBox<>(this.persons);
        payerComboBox.setSelectedItem(selectedPerson);

        this.addObjects(un_evenCombobox, container, layout, gbc, 0, 0, 1, 1);

        gbc.anchor = GridBagConstraints.EAST;
        this.addObjects(new JLabel("Payer: "), container, layout, gbc, 2, 0, 1, 1);

        gbc.anchor = GridBagConstraints.CENTER;
        this.addObjects(payerComboBox, container, layout, gbc, 3, 0, 1, 1);

        this.addObjects(nameLabel, container, layout, gbc, 0, 1, 4, 1);
        this.addObjects(ticketName, container, layout, gbc, 0, 2, 4, 1);

        this.addObjects(typeLabel, container, layout, gbc, 0, 3, 4, 1);
        this.addObjects(ticketType, container, layout, gbc, 0, 4, 4, 1);

        int i;
        textFields = new ArrayList<>();
        for(i=0; i<persons.length; i++){
            // Put the text fields for each person in an arraylist => we can access it later
            textFields.add(new JTextField(20));
            this.addObjects(new JLabel(persons[i].getName()), container, layout, gbc, 0, 5+i, 1, 1);
            this.addObjects(textFields.get(i), container, layout, gbc, 1, 5+i, 3, 1);
        }

        gbc.anchor = GridBagConstraints.NORTHEAST;
        this.addObjects(okButton, container, layout, gbc, 3, 6+i, 1, 1);

        okButton.addActionListener(e -> switchToMainFrame());

        un_evenCombobox.addActionListener(e -> changeTicketType());

        this.setLocationRelativeTo(null);
    }

    // Change to even ticket frame if selected in the dropdown menu
    private void changeTicketType(){
        String type = (String) un_evenCombobox.getSelectedItem();
        // Check empty string
        if(type != null) {
            // Switch to the other ticket frame
            if (type.equals("even ticket")) {
                addEvenTicketFrame evenTicketFrame = new addEvenTicketFrame("Add Ticket", this.parent, personsModel, (Person) this.payerComboBox.getSelectedItem());
                this.setVisible(false);
                evenTicketFrame.setVisible(true);
            }
        }
    }

    // Add the ticket and switch to the main frame
    private void switchToMainFrame(){
        // If text fields are empty => exception => try catch
        try {
            String ticketNameText = ticketName.getText();
            String ticketTypeText = ticketType.getText();
            Person payer = (Person) this.payerComboBox.getSelectedItem();

            ticketFactory fact = new ticketFactory();

            // Store the debt of the persons to the payer
            HashMap<Person, Double> temp = new HashMap<>();

            // Fill up the map with the values stored in the text fields from the arraylist
            for (int i = 0; i < personsModel.size(); i++) {
                temp.put(personsModel.get(i), Double.parseDouble(this.textFields.get(i).getText()));
            }

            // Try to add the ticket, wrong type => exception => try catch
            try {
                fact.addTicket(ticketTypeText, ticketNameText, payer, temp);
            } catch (Exception e) {
                // error window
                JOptionPane.showMessageDialog(this, "Wrong ticket type\n Chose between: restaurant, ...");
            }
        } catch (Exception e) {
            // error window
            JOptionPane.showMessageDialog(this, "Something went wrong");
        }

        // Change visibility
        this.setVisible(false);
        parent.setVisible(true);
        // Ticket added => refresh payment mapping
        parent.refresh();
    }

    // Function to simplify the process of adding constrains to components
    public void addObjects(Component component, Container container, GridBagLayout layout, GridBagConstraints gbc, int gridx, int gridy, int gridwidth, int gridheight){
        // https://stackoverflow.com/questions/30656473/how-to-use-gridbaglayout
        gbc.gridx = gridx;
        gbc.gridy = gridy;

        gbc.gridwidth = gridwidth;
        gbc.gridheight = gridheight;

        layout.setConstraints(component, gbc);
        container.add(component);
    }
}
