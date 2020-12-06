package gui.frames;

import database.PersonDB;
import factory.personFactory;
import factory.ticketFactory;
import person.Person;
import ticket.Ticket;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.Vector;

public class addUnevenTicketFrame extends JFrame{
    private JPanel container;

    private JButton okButton;
    private JLabel nameLabel;
    private JLabel typeLabel;
    private JTextField ticketName;
    private JTextField ticketType;

    private JComboBox<Person> payerComboBox;
    private JComboBox<String> un_evenCombobox;

    private final String[] T = {"uneven ticket", "even ticket"};
    private Person[] persons;
    private DefaultListModel<Person> personsModel;

    private Person selectedPerson;

    private mainFrame parent;

    public addUnevenTicketFrame(String title, mainFrame parent, DefaultListModel<Person> persons, Person selectedPerson){
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
        for(i=0; i<persons.length; i++){
            this.addObjects(new JLabel(persons[i].getName()), container, layout, gbc, 0, 5+i, 1, 1);
            this.addObjects(new JTextField(20), container, layout, gbc, 1, 5+i, 3, 1);
        }

        gbc.anchor = GridBagConstraints.NORTHEAST;
        this.addObjects(okButton, container, layout, gbc, 3, 6+i, 1, 1);

        okButton.addActionListener(e -> switchToMainFrame());

        un_evenCombobox.addActionListener(e -> changeTicketType());

        this.setLocationRelativeTo(null);
    }

    private void changeTicketType(){
        String type = (String) un_evenCombobox.getSelectedItem();
        assert type != null;
        if(type.equals("even ticket")){
            addEvenTicketFrame evenTicketFrame = new addEvenTicketFrame("Add Ticket", this.parent, personsModel, (Person) this.payerComboBox.getSelectedItem());
            this.setVisible(false);
            evenTicketFrame.setVisible(true);
        }
    }

    private void switchToMainFrame(){
        String ticketNameText = ticketName.getText();
        String ticketTypeText = ticketType.getText();
        ticketFactory fact = new ticketFactory();

        try{
            fact.addTicket(ticketTypeText, ticketNameText,(Person) payerComboBox.getSelectedItem(), new HashMap<>());
        } catch (Exception e){
            JOptionPane.showMessageDialog(this, "Wrong ticket type\n Chose between: restaurant, ...");
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
