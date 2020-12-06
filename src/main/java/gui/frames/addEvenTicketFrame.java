package gui.frames;

import database.PersonDB;
import factory.personFactory;
import factory.ticketFactory;
import person.Person;
import ticket.Ticket;

import javax.swing.*;
import java.awt.*;
import java.util.Vector;

public class addEvenTicketFrame extends JFrame{
    private JPanel container;

    private JButton okButton;
    private JLabel nameLabel;
    private JLabel typeLabel;
    private JLabel priceLabel;
    private JTextField ticketName;
    private JTextField ticketType;
    private JTextField totalPrice;

    private JComboBox<Person> payerComboBox;
    private JComboBox<String> un_evenCombobox;

    private final String[] T = {"even ticket", "uneven ticket"};
    private Person[] persons;
    private DefaultListModel<Person> personsModel;

    private Person selectedPerson;

    private mainFrame parent;

    public addEvenTicketFrame(String title, mainFrame parent, DefaultListModel<Person> persons, Person selectedPerson){
        super(title);
        this.parent = parent;
        this.personsModel = persons;
        this.selectedPerson = selectedPerson;
        this.persons = new Person[persons.getSize()];
        for(int i=0; i< persons.getSize(); i++){
            this.persons[i] = persons.get(i);
        }
        initialize();
    }

    public void initialize(){
        this.setSize(500, 300);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        container = new JPanel();
        GridBagLayout layout = new GridBagLayout();
        GridBagConstraints gbc = new GridBagConstraints();

        container.setLayout(layout);
        getContentPane().add(container);

        nameLabel = new JLabel("Name of the ticket:");
        this.ticketName = new JTextField(30);
        typeLabel = new JLabel("Type of the ticket:");
        this.ticketType = new JTextField(30);
        priceLabel = new JLabel("Total Price: ");
        this.totalPrice = new JTextField(20);
        okButton = new JButton("Add");


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

        okButton.addActionListener(e -> switchToMainFrame());

        un_evenCombobox.addActionListener(e -> changeTicketType());

        totalPrice.addActionListener(e -> switchToMainFrame());

        this.setLocationRelativeTo(null);
    }

    private void changeTicketType(){
        String type = (String) un_evenCombobox.getSelectedItem();
        assert type != null;
        if(type.equals("uneven ticket")){
            addUnevenTicketFrame unevenTicketFrame = new addUnevenTicketFrame("Add Ticket", this.parent, personsModel, (Person) this.payerComboBox.getSelectedItem());
            this.setVisible(false);
            unevenTicketFrame.setVisible(true);
        }
    }

    private void switchToMainFrame(){
        try {
            String ticketNameText = this.ticketName.getText();
            String ticketTypeText = this.ticketType.getText();
            int total_Price = Integer.parseInt(totalPrice.getText());
            Person payer = (Person) this.payerComboBox.getSelectedItem();
            ticketFactory fact = new ticketFactory();

            try {
                fact.addTicket(ticketTypeText, ticketNameText, payer, total_Price);
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Wrong ticket type\n Chose between: plane, ...");
            }
        } catch (Exception e){
            JOptionPane.showMessageDialog(this, "Something went wrong");
        }

        this.setVisible(false);
        parent.setVisible(true);
        parent.refresh();
    }

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
